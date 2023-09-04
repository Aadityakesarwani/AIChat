package com.innovativetools.conversai.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.innovativetools.conversai.data.model.ConversationModel
import com.innovativetools.conversai.data.model.MessageModel

@Database(
    entities = [ConversationModel::class, MessageModel::class],
    version = 1,
    exportSchema = false
)
abstract class ConversAIDatabase : RoomDatabase() {
    abstract fun conversAIDao(): ConversAIDao
}