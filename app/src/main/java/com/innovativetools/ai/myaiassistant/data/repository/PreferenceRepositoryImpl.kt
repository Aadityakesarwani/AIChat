package com.innovativetools.ai.myaiassistant.data.repository

import android.app.Application
import android.content.SharedPreferences
import com.innovativetools.ai.myaiassistant.data.model.GPTModel
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.utils.Constants.Preferences.FREE_MESSAGE_COUNT_DEFAULT
import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import java.util.*
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val app: Application
) : PreferenceRepository {
    override fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.DARK_MODE, isDarkMode).apply()
    }

    override fun getDarkMode(): Boolean {
        return sharedPreferences.getBoolean(
            Constants.Preferences.DARK_MODE,
            true
        )
    }

    override fun setCurrentLanguage(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_NAME, language).apply()
    }

    override fun getCurrentLanguage(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_NAME,
            Locale.getDefault().displayLanguage
        ) ?: Locale.getDefault().displayLanguage

    override fun setCurrentLanguageCode(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_CODE, language).apply()
    }

    override fun getCurrentLanguageCode(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_CODE,
            Locale.getDefault().language
        ) ?: Locale.getDefault().language

    override fun isProVersion(): Boolean =
        sharedPreferences.getBoolean(
            Constants.Preferences.PRO_VERSION,
            false
        )

    override fun setProVersion(isProVersion: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.PRO_VERSION, isProVersion)
            .apply()
    }

    override fun isFirstTime(): Boolean =
        sharedPreferences.getBoolean(
            Constants.Preferences.FIRST_TIME,
            true
        )

    override fun setFirstTime(isFirstTime: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.FIRST_TIME, isFirstTime)
            .apply()
    }


    override fun getFreeMessageCount(): Int {
        if (!sharedPreferences.contains(Constants.Preferences.FREE_MESSAGE_COUNT)) {
            setFreeMessageCount(FREE_MESSAGE_COUNT_DEFAULT)
        }

        // Get the saved free message count.
        return sharedPreferences.getInt(
            Constants.Preferences.FREE_MESSAGE_COUNT,
            FREE_MESSAGE_COUNT_DEFAULT
        )
    }

    override fun setFreeMessageCount(count: Int) {
        sharedPreferences.edit().putInt(Constants.Preferences.FREE_MESSAGE_COUNT, count)
            .apply()
    }

    override fun getGPTModel(): GPTModel {
        val modelString = sharedPreferences.getString(Constants.Preferences.GPT_MODEL, GPTModel.gpt35Turbo.model)
        return GPTModel.values().firstOrNull { it.model == modelString } ?: GPTModel.gpt35Turbo
    }

    override fun setGPTModel(model: GPTModel) {
        sharedPreferences.edit().putString(Constants.Preferences.GPT_MODEL, model.model).apply()
    }
}


//private val GPT_MODEL_KEY = "gpt_model"
//
//
//override fun getGPTModel(): GPTModel {
//    val modelString = sharedPreferences.getString(GPT_MODEL_KEY, GPTModel.gpt35Turbo.model)
//    return GPTModel.values().firstOrNull { it.model == modelString } ?: GPTModel.gpt35Turbo
//}
//
//override fun setGPTModel(model: GPTModel) {
//    sharedPreferences.edit().putString(GPT_MODEL_KEY, model.model).apply()
//}





//    override fun getFreeMessageCount(): Int {
//        // Get the saved free message count and last checked time from SharedPreferences.
//        val savedCount = sharedPreferences.getInt(
//            Constants.Preferences.FREE_MESSAGE_COUNT,
//            FREE_MESSAGE_COUNT_DEFAULT
//        )
//        val lastCheckedTime = sharedPreferences.getLong(
//            Constants.Preferences.FREE_MESSAGE_LAST_CHECKED_TIME,
//            0L
//        )
//
//        // Check if the last checked time was yesterday or earlier.
//        val currentTime = System.currentTimeMillis()
//        val lastCheckedCalendar = Calendar.getInstance().apply { timeInMillis = lastCheckedTime }
//        val currentCalendar = Calendar.getInstance().apply { timeInMillis = currentTime }
//        if (lastCheckedCalendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR) ||
//            lastCheckedCalendar.get(Calendar.YEAR) != currentCalendar.get(Calendar.YEAR)
//        ) {
//            // If last checked time was yesterday or earlier, reset the free message count to 3.
//            sharedPreferences.edit()
//                .putInt(Constants.Preferences.FREE_MESSAGE_COUNT, FREE_MESSAGE_COUNT_DEFAULT)
//                .putLong(Constants.Preferences.FREE_MESSAGE_LAST_CHECKED_TIME, currentTime)
//                .apply()
//            return FREE_MESSAGE_COUNT_DEFAULT
//        }
//        return savedCount
//    }
