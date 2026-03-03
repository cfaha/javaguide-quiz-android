package com.openclaw.javaguidesquiz

import android.app.Application
import androidx.room.Room
import com.openclaw.javaguidesquiz.data.local.AppDatabase

class QuizApp : Application() {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "quiz.db"
        ).fallbackToDestructiveMigration().build()
    }
}
