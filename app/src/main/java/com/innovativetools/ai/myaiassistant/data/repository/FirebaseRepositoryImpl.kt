package com.innovativetools.ai.myaiassistant.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.innovativetools.ai.myaiassistant.BuildConfig
import com.innovativetools.ai.myaiassistant.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirebaseRepository {
    override suspend fun isThereUpdate(): Boolean {
//        try {
//            val current_version: Int = BuildConfig.VERSION_CODE
//            Log.e("version_code", current_version.toString())
//
//            val version = firestore.collection("app").document("app_info")
//                .get().await()
//            val versionCode = version.data?.get("app_version_code").toString().toInt()
//            Log.e("version_code", version.toString())
//
//            return versionCode != current_version
//
//        } catch (e: Exception) {
//            Log.e("Exception", e.toString())
//            return false
//        }

        return false
    }
}