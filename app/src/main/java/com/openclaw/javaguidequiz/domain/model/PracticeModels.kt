package com.openclaw.javaguidesquiz.domain.model

data class PracticeState(
    val questions: List<Question> = emptyList(),
    val index: Int = 0,
    val selectedOptions: Set<Int> = emptySet(),
    val blankInput: String = "",
    val showResult: Boolean = false,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val favorites: Set<String> = emptySet(),
    val wrongBook: Set<String> = emptySet()
) {
    val current: Question? get() = questions.getOrNull(index)
    val total: Int get() = questions.size
}
