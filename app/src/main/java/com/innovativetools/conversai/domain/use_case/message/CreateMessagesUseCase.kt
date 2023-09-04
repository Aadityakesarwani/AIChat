package com.innovativetools.conversai.domain.use_case.message

import com.innovativetools.conversai.data.model.MessageModel
import com.innovativetools.conversai.domain.repository.MessageRepository
import javax.inject.Inject

class CreateMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: MessageModel) =
        messageRepository.addMessage(message)
}