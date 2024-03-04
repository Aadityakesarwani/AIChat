package com.innovativetools.ai.myaiassistant.domain.use_case.message

import com.innovativetools.ai.myaiassistant.data.model.MessageModel
import com.innovativetools.ai.myaiassistant.domain.repository.MessageRepository
import javax.inject.Inject

class CreateMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: MessageModel) =
        messageRepository.addMessage(message)
}