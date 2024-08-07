package com.innovativetools.ai.myaiassistant.domain.use_case.message

import com.innovativetools.ai.myaiassistant.data.model.TextCompletionsParam
import com.innovativetools.ai.myaiassistant.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TextCompletionsWithStreamUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(scope: CoroutineScope, params: TextCompletionsParam) =
        chatRepository.textCompletionsWithStream(scope, params)
}