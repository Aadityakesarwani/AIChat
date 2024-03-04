package com.innovativetools.ai.myaiassistant.di

import com.innovativetools.ai.myaiassistant.data.repository.ChatRepositoryImpl
import com.innovativetools.ai.myaiassistant.data.repository.ConversationRepositoryImpl
import com.innovativetools.ai.myaiassistant.data.repository.FirebaseRepositoryImpl
import com.innovativetools.ai.myaiassistant.data.repository.MessageRepositoryImpl
import com.innovativetools.ai.myaiassistant.data.repository.PreferenceRepositoryImpl
import com.innovativetools.ai.myaiassistant.domain.repository.ChatRepository
import com.innovativetools.ai.myaiassistant.domain.repository.ConversationRepository
import com.innovativetools.ai.myaiassistant.domain.repository.FirebaseRepository
import com.innovativetools.ai.myaiassistant.domain.repository.MessageRepository
import com.innovativetools.ai.myaiassistant.domain.repository.PreferenceRepository
import com.innovativetools.ai.myaiassistant.data.repository.*
import com.innovativetools.ai.myaiassistant.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    abstract fun provideConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    abstract fun provideMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    @Binds
    abstract fun providePreferenceRepository(preferenceRepositoryImpl: PreferenceRepositoryImpl): PreferenceRepository

    @Binds
    abstract fun provideFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

}