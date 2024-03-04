package com.innovativetools.ai.myaiassistant.domain.repository

import com.innovativetools.ai.myaiassistant.data.model.TextCompletionsParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun textCompletionsWithStream(scope: CoroutineScope, params: TextCompletionsParam): Flow<String>
}