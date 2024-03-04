package com.innovativetools.ai.myaiassistant

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale


@HiltAndroidApp
class MyAiAssistant : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
    companion object {
        fun updateLanguage(context: Context, language: String?) {
            val locale = if (language != null) Locale(language) else Locale.getDefault()
            Locale.setDefault(locale)

            val resources = context.resources
            val config = Configuration(resources.configuration)
            config.setLocale(locale)

            context.createConfigurationContext(config)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}





//@HiltAndroidApp
//class MyAiAssistant : Application() {
//    private lateinit var preferenceRepository: PreferenceRepositoryImpl
//
//    override fun attachBaseContext(base: Context) {
//        preferenceRepository = PreferenceRepositoryImpl(base.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE), this)
//        val savedLanguageCode = preferenceRepository.getCurrentLanguageCode()
//        super.attachBaseContext(updateBaseContextLocale(base, savedLanguageCode))
//    }
//
//    private fun updateBaseContextLocale(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val resources = context.resources
//        val config = Configuration(resources.configuration)
//        config.setLocale(locale)
//
//        return context.createConfigurationContext(config)
//    }
//}
