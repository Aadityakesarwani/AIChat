package com.innovativetools.ai.myaiassistant.data.repository

import com.innovativetools.ai.myaiassistant.data.model.ConversationModel
import com.innovativetools.ai.myaiassistant.data.source.local.MyAIAssistantDao
import com.innovativetools.ai.myaiassistant.domain.repository.ConversationRepository
import javax.inject.Inject


class ConversationRepositoryImpl @Inject constructor(
    private val myAIAssistantDao: MyAIAssistantDao

) : ConversationRepository {
    override suspend fun getConversations(): MutableList<ConversationModel> =
        myAIAssistantDao.getConversations()

    override suspend fun addConversation(conversation: ConversationModel) =
        myAIAssistantDao.addConversation(conversation)

    override suspend fun deleteConversation(conversationId: String) {
        myAIAssistantDao.deleteConversation(conversationId)
        myAIAssistantDao.deleteMessages(conversationId)
    }


    override suspend fun deleteAllConversation() = myAIAssistantDao.deleteAllConversation()

}