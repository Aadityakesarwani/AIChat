package com.innovativetools.ai.myaiassistant.domain.use_case.model

import com.innovativetools.ai.myaiassistant.data.model.GPTModel
import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetGptModelUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(gptModel: GPTModel) = preferenceRepository.setGPTModel(gptModel)
}
