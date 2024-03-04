package com.innovativetools.ai.myaiassistant.ui.myassistants

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovativetools.ai.myaiassistant.data.model.AiAssistantModel
import com.innovativetools.ai.myaiassistant.domain.use_case.language.GetCurrentLanguageCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAssistantsViewModel @Inject constructor (
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
//  private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) : ViewModel() {
    val showVertical = mutableStateOf(false)
    val verticalShowList = mutableStateListOf<AiAssistantModel>()
    val selectedValue = mutableStateOf("")
    val currentLanguageCode = mutableStateOf("en")

    fun navigateToChat(navigate: () -> Unit) {
//        viewModelScope.launch(dispatcher) {
            navigate()
//        }
    }

    fun getCurrentLanguageCode() = viewModelScope.launch {
        currentLanguageCode.value = getCurrentLanguageCodeUseCase()
    }

}


