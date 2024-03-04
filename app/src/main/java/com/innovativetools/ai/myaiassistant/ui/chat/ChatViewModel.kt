package com.innovativetools.ai.myaiassistant.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.data.model.ConversationModel
import com.innovativetools.ai.myaiassistant.data.model.MessageModel
import com.innovativetools.ai.myaiassistant.data.model.MessageTurbo
import com.innovativetools.ai.myaiassistant.data.model.TextCompletionsParam
import com.innovativetools.ai.myaiassistant.data.model.TurboRole
import com.innovativetools.ai.myaiassistant.domain.use_case.conversation.CreateConversationUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.message.CreateMessagesUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.message.GetFreeMessageCountUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.message.GetMessagesUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.message.SetFreeMessageCountUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.message.TextCompletionsWithStreamUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.model.GetGptModelUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.IsProVersionUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.SetProVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.HashMap
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val textCompletionsWithStreamUseCase: TextCompletionsWithStreamUseCase,
    private val createMessagesUseCase: CreateMessagesUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val createConversationUseCase: CreateConversationUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val isProVersionUseCase: IsProVersionUseCase,
    private val getFreeMessageCountUseCase: GetFreeMessageCountUseCase,
    private val setFreeMessageCountUseCase: SetFreeMessageCountUseCase,
    private val setProVersionUseCase: SetProVersionUseCase,
    private val getGptModelUseCase: GetGptModelUseCase

) : ViewModel() {

    private var answerFromGPT = ""
    private var newMessageModel = MessageModel()

    private val cScope = CoroutineScope(Dispatchers.IO)
    var job: Job? = null

    private val _currentConversation: MutableStateFlow<String> =
        MutableStateFlow(Date().time.toString())

    private val _messages: MutableStateFlow<HashMap<String, MutableList<MessageModel>>> =
        MutableStateFlow(HashMap())

    private val _isGenerating: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val currentConversationState: StateFlow<String> = _currentConversation.asStateFlow()
    val messagesState: StateFlow<HashMap<String, MutableList<MessageModel>>> =
        _messages.asStateFlow()
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()


    private val _isProVersion = MutableStateFlow(false)
    val isProVersion get() = _isProVersion.asStateFlow()

    private val _freeMessageCount = MutableStateFlow(3)
    val freeMessageCount get() = _freeMessageCount.asStateFlow()

    val showAdsAndProVersion = mutableStateOf(false)

    init {
        _currentConversation.value = savedStateHandle.get<String>("id")
            ?: Date().time.toString()
        viewModelScope.launch { fetchMessages() }
        initializeBotInstructionMessage()
    }





    private fun initializeBotInstructionMessage() {
        val botInstructionMessage = MessageModel(
            id = "bot_instruction_id", // A constant id to recognize the bot instruction message
            conversationId = _currentConversation.value,
            question = "", // Bot message doesn't have a question, so leave it empty
            answer = "Your bot instructions go here...", // Bot instructions text
            createdAt = Calendar.getInstance().time.toString()
        )

        // Add this message as the first message in the conversation
        val messages = mutableListOf(botInstructionMessage)
        messages.addAll(getMessagesByConversation(_currentConversation.value))
        setMessages(messages)
    }











    fun setProVersion(isPro: Boolean) {
        setProVersionUseCase(isPro)
        _isProVersion.value = isPro
        showAdsAndProVersion.value = false
    }

    fun getProVersion() = viewModelScope.launch {
        _isProVersion.value = isProVersionUseCase()
        if (_isProVersion.value) {
            showAdsAndProVersion.value = false
        }
    }

    fun getFreeMessageCount() = viewModelScope.launch {
        _freeMessageCount.value = getFreeMessageCountUseCase()
        Log.e("freeMessageCount", _freeMessageCount.value.toString())
    }

    fun decreaseFreeMessageCount() {
        viewModelScope.launch {
            _freeMessageCount.value = _freeMessageCount.value - 1
            setFreeMessageCountUseCase(_freeMessageCount.value)
        }
    }

    fun increaseFreeMessageCount() {
        viewModelScope.launch {
            _freeMessageCount.value = _freeMessageCount.value + Constants.Preferences.INCREASE_MESSAGE_COUNT
            setFreeMessageCountUseCase(_freeMessageCount.value)
        }
    }

    fun increaseThreeFreeMessageCount() {
        viewModelScope.launch {
            _freeMessageCount.value = _freeMessageCount.value + Constants.Preferences.INCREASE_THREE_MESSAGE_COUNT
            setFreeMessageCountUseCase(_freeMessageCount.value)
        }
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        if (getMessagesByConversation(_currentConversation.value).isEmpty()) {
            createConversationRemote(message)
        }

        newMessageModel = MessageModel(
            question = message,
            answer = "...",
            conversationId = _currentConversation.value,
        )

        val currentListMessage: MutableList<MessageModel> =
            getMessagesByConversation(_currentConversation.value).toMutableList()

        // Insert message to list
        currentListMessage.add(0, newMessageModel)
        setMessages(currentListMessage)


        // Execute API OpenAI
        val flow: Flow<String> = textCompletionsWithStreamUseCase(
            scope = cScope,
            TextCompletionsParam(
                promptText = getPrompt(_currentConversation.value),
                messagesTurbo = getMessagesParamsTurbo(_currentConversation.value),
                model = getGptModelUseCase.invoke()
            )
        )


        answerFromGPT = ""

        job = cScope.launch {
            _isGenerating.value = true
            flow.collect {
                answerFromGPT += it
                updateLocalAnswer(answerFromGPT.trim())
            }
            // Save to Firestore
            createMessagesUseCase(newMessageModel.copy(answer = answerFromGPT))
            _isGenerating.value = false
        }


    }

    fun stopGenerate() = viewModelScope.launch {
        job?.cancel()
        _isGenerating.value = false
        createMessagesUseCase(newMessageModel.copy(answer = answerFromGPT))
    }

    private fun createConversationRemote(title: String) = viewModelScope.launch {
        val newConversation = ConversationModel(
            id = _currentConversation.value,
            title = title,
            createdAt = Calendar.getInstance().time.toString()
        )
        createConversationUseCase(newConversation)
    }
    private fun getMessagesByConversation(conversationId: String): MutableList<MessageModel> {
        if (_messages.value[conversationId] == null) return mutableListOf()

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        return messagesMap[conversationId]!!
    }

    private fun getPrompt(conversationId: String): String {
        if (_messages.value[conversationId] == null) return ""

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        var response: String = ""

        for (message in messagesMap[conversationId]!!.reversed()) {
            response += """
            Human:${message.question.trim()}
            Bot:${if (message.answer == "...") "" else message.answer.trim()}"""
        }
        return response
    }
    private fun getMessagesParamsTurbo(conversationId: String): List<MessageTurbo> {
        if (_messages.value[conversationId] == null) return listOf()

        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>
        val response: MutableList<MessageTurbo> = mutableListOf(
            MessageTurbo(
                role = TurboRole.system,
                content = Constants.DEFAULT_AI
            )
        )
        for (message in messagesMap[conversationId]!!.reversed()) {
            response.add(MessageTurbo(content = message.question))

            if (message.answer != "...") {
                response.add(MessageTurbo(content = message.answer, role = TurboRole.user))
            }
        }
        return response.toList()
    }

    private suspend fun fetchMessages() {
        if (_currentConversation.value.isEmpty() || _messages.value[_currentConversation.value] != null) return

        val list: List<MessageModel> = getMessagesUseCase(_currentConversation.value)

        setMessages(list.toMutableList())

    }

    private fun updateLocalAnswer(answer: String) {
        val currentListMessage: MutableList<MessageModel> =
            getMessagesByConversation(_currentConversation.value).toMutableList()

        currentListMessage[0] = currentListMessage[0].copy(answer = answer)

        setMessages(currentListMessage)
    }

    private fun setMessages(messages: MutableList<MessageModel>) {
        val messagesMap: HashMap<String, MutableList<MessageModel>> =
            _messages.value.clone() as HashMap<String, MutableList<MessageModel>>

        messagesMap[_currentConversation.value] = messages

        _messages.value = messagesMap
    }
}



























//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.innovativetools.ai.myaiassistant.domain.use_case.app.IsThereUpdateUseCase
//import com.innovativetools.ai.myaiassistant.domain.use_case.language.GetCurrentLanguageCodeUseCase
//import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.IsFirstTimeUseCase
//import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.IsProVersionUseCase
//import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.SetFirstTimeUseCase
//import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.SetProVersionUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class StartChatViewModel @Inject constructor(
//    private val isProVersionUseCase: IsProVersionUseCase,
//    private val setProVersionUseCase: SetProVersionUseCase,
//    private val isFirstTimeUseCase: IsFirstTimeUseCase,
//    private val setFirstTimeUseCase: SetFirstTimeUseCase,
//    private val isThereUpdateUseCase: IsThereUpdateUseCase,
//    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
//) :
//    ViewModel() {
//
//    val isProVersion = mutableStateOf(false)
//    val isFirstTime = mutableStateOf(true)
//    val isThereUpdate = mutableStateOf(false)
//    val currentLanguageCode = mutableStateOf("en")
//
//
//    fun isThereUpdate() = viewModelScope.launch {
//        isThereUpdate.value = isThereUpdateUseCase()
//    }
//
//    fun getProVersion() = viewModelScope.launch {
//        isProVersion.value = isProVersionUseCase()
//    }
//
//    fun getFirstTime() = viewModelScope.launch {
//        isFirstTime.value = isFirstTimeUseCase()
//    }
//
//    fun setProVersion(isPro: Boolean) {
//        setProVersionUseCase(isPro)
//        isProVersion.value = isPro
//    }
//
//    fun setFirstTime(isFirstTime: Boolean) = setFirstTimeUseCase(isFirstTime)
//
//
//    fun getCurrentLanguageCode() = viewModelScope.launch {
//        currentLanguageCode.value = getCurrentLanguageCodeUseCase()
//    }
//}