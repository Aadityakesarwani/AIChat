package com.innovativetools.ai.myaiassistant.domain.repository

import com.innovativetools.ai.myaiassistant.data.model.MessageModel

interface MessageRepository {
    suspend fun getMessages(conversationId: String): List<MessageModel>
    suspend fun addMessage(message: MessageModel)
    suspend fun deleteMessages(conversationId: String)
}