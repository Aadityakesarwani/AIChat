package com.innovativetools.ai.myaiassistant.domain.use_case.conversation

import com.innovativetools.ai.myaiassistant.data.model.ConversationModel
import com.innovativetools.ai.myaiassistant.domain.repository.ConversationRepository
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationModel) =
        conversationRepository.addConversation(conversation)
}