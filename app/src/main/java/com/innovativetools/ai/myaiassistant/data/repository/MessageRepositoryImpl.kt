package com.innovativetools.ai.myaiassistant.data.repository

import com.innovativetools.ai.myaiassistant.data.model.MessageModel
import com.innovativetools.ai.myaiassistant.data.source.local.MyAIAssistantDao
import com.innovativetools.ai.myaiassistant.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val myAIAssistantDao: MyAIAssistantDao,
) : MessageRepository {
    override suspend fun getMessages(conversationId: String): List<MessageModel> =
        myAIAssistantDao.getMessages(conversationId)

    override suspend fun addMessage(message: MessageModel) =
        myAIAssistantDao.addMessage(message)

    override suspend fun deleteMessages(conversationId: String) =
        myAIAssistantDao.deleteMessages(conversationId)
}