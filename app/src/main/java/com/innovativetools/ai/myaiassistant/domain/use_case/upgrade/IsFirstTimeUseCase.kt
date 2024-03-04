package com.innovativetools.ai.myaiassistant.domain.use_case.upgrade

import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import javax.inject.Inject

class IsFirstTimeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() = preferenceRepository.isFirstTime()
}