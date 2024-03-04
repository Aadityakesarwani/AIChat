package com.innovativetools.ai.myaiassistant.di

import android.content.Context
import androidx.room.Room
import com.innovativetools.ai.myaiassistant.data.source.local.MyAIAssistantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {


    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context): MyAIAssistantDatabase =
        Room.databaseBuilder(
            appContext,
            MyAIAssistantDatabase::class.java,
            "myAiAssistantdb.db"
        ).build()

    @Provides
    @Singleton
    fun provideMyAssistantDao(myAIAssistantDatabase: MyAIAssistantDatabase) = myAIAssistantDatabase.myAiAssistantDao()
}