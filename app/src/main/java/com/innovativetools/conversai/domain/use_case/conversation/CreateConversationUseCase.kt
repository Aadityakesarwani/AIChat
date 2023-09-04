package com.innovativetools.conversai.domain.use_case.conversation

import com.innovativetools.conversai.data.model.ConversationModel
import com.innovativetools.conversai.domain.repository.ConversationRepository
import javax.inject.Inject

class CreateConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversation: ConversationModel) =
        conversationRepository.addConversation(conversation)
}