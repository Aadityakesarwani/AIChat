package com.innovativetools.ai.myaiassistant.ui.upgrade

import android.util.Log
import androidx.lifecycle.ViewModel
import com.innovativetools.ai.myaiassistant.data.model.GPTModel
import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import com.innovativetools.ai.myaiassistant.domain.use_case.model.GetGptModelUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.model.SetGptModelUseCase
import com.innovativetools.ai.myaiassistant.domain.use_case.upgrade.SetProVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class UpgradeViewModel @Inject constructor(
    private val setProVersionUseCase: SetProVersionUseCase,
    private val setGptModelUseCase: SetGptModelUseCase,
    val getGptModelUseCase: GetGptModelUseCase
) : ViewModel() {

fun setProVersion(isProVersion: Boolean) {
    setProVersionUseCase(isProVersion)
    if (isProVersion) {
        setGptModelUseCase(GPTModel.gpt4)
    }else{
        setGptModelUseCase(GPTModel.gpt35Turbo)
    }
//    Log.d("GPTModel", "Current model: ${getGptModelUseCase.invoke().model}")
}

}

//    fun setProVersion(isProVersion: Boolean) = setProVersionUseCase(isProVersion)


