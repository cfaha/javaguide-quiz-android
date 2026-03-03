package com.openclaw.javaguidesquiz.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val type: String,
    val category: String,
    val stem: String,
    val answersJson: String,
    val explanation: String,
    val difficulty: Int,
    val sourcePath: String
)

@Entity(tableName = "question_option", primaryKeys = ["questionId", "idx"])
data class QuestionOptionEntity(
    val questionId: String,
    val idx: Int,
    val content: String
)

@Entity(tableName = "wrong_book", primaryKeys = ["questionId"])
data class WrongBookEntity(
    val questionId: String,
    val wrongCount: Int,
    val lastWrongAt: Long
)
