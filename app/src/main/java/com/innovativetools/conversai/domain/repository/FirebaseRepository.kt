package com.innovativetools.conversai.domain.repository

interface FirebaseRepository {
    suspend fun isThereUpdate(): Boolean
}