package com.innovativetools.ai.myaiassistant.domain.use_case.conversation

import com.innovativetools.ai.myaiassistant.domain.repository.ConversationRepository
import javax.inject.Inject

class GetConversationsUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke() =
        conversationRepository.getConversations()
}