package com.openclaw.javaguidesquiz.domain.model

enum class PracticeMode { SEQUENTIAL, RANDOM }

data class PracticeState(
    val allQuestions: List<Question> = emptyList(),
    val questions: List<Question> = emptyList(),
    val selectedCategory: String = "全部",
    val mode: PracticeMode = PracticeMode.SEQUENTIAL,
    val started: Boolean = false,
    val index: Int = 0,
    val selectedOptions: Set<Int> = emptySet(),
    val blankInput: String = "",
    val showResult: Boolean = false,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val completed: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val wrongBook: Set<String> = emptySet()
) {
    val current: Question? get() = questions.getOrNull(index)
    val total: Int get() = questions.size
    val categories: List<String> get() = listOf("全部") + allQuestions.map { it.category }.distinct()
}
