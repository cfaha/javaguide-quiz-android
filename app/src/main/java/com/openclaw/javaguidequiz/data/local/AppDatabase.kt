package com.openclaw.javaguidesquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QuestionEntity::class, QuestionOptionEntity::class, WrongBookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}
