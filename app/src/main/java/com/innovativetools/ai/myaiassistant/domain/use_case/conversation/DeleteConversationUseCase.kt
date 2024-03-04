package com.innovativetools.ai.myaiassistant.domain.use_case.conversation

import com.innovativetools.ai.myaiassistant.domain.repository.ConversationRepository
import javax.inject.Inject

class DeleteConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String) =
        conversationRepository.deleteConversation(conversationId)
}