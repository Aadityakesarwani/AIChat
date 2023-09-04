package com.innovativetools.conversai.domain.use_case.message

import com.innovativetools.conversai.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(conversationId: String) =
        messageRepository.getMessages(conversationId = conversationId)
}