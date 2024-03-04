package com.innovativetools.ai.myaiassistant.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.innovativetools.ai.myaiassistant.data.model.ConversationModel
import com.innovativetools.ai.myaiassistant.data.model.MessageModel

@Database(
    entities = [ConversationModel::class, MessageModel::class],
    version = 1,
    exportSchema = false
)
abstract class MyAIAssistantDatabase : RoomDatabase() {
    abstract fun myAiAssistantDao(): MyAIAssistantDao
}