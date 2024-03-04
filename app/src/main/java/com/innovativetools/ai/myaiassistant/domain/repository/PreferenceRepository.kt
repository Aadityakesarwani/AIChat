package com.innovativetools.ai.myaiassistant.domain.repository

import com.innovativetools.ai.myaiassistant.data.model.GPTModel

interface PreferenceRepository {
    fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Boolean
    fun setCurrentLanguage(language: String)
    fun getCurrentLanguage(): String
    fun setCurrentLanguageCode(language: String)
    fun getCurrentLanguageCode(): String
    fun isProVersion(): Boolean
    fun setProVersion(isProVersion: Boolean)
    fun isFirstTime(): Boolean
    fun setFirstTime(isFirstTime: Boolean)
    fun getFreeMessageCount(): Int
    fun setFreeMessageCount(count: Int)

    fun getGPTModel(): GPTModel
    fun setGPTModel(model: GPTModel)

}