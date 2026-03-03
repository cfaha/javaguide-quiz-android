package com.openclaw.javaguidesquiz.domain.model

enum class QuestionType { SINGLE, MULTI, BLANK }

data class Question(
    val id: String,
    val type: QuestionType,
    val category: String,
    val stem: String,
    val options: List<String> = emptyList(),
    val answers: List<String>,
    val explanation: String,
    val difficulty: Int = 1
)
