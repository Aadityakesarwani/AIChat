package com.innovativetools.ai.myaiassistant.domain.use_case.upgrade

import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFirstTimeUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(isFirstTime: Boolean) = preferenceRepository.setFirstTime(isFirstTime)
}
