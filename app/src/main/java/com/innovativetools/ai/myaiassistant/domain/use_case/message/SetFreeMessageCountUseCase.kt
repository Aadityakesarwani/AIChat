package com.innovativetools.ai.myaiassistant.domain.use_case.message

import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFreeMessageCountUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(count: Int) = preferenceRepository.setFreeMessageCount(count)
}