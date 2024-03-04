package com.innovativetools.ai.myaiassistant.domain.repository

interface FirebaseRepository {
    suspend fun isThereUpdate(): Boolean
}