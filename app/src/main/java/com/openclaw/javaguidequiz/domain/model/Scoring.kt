package com.openclaw.javaguidesquiz.domain.model

object Scoring {
    fun check(question: Question, selectedOptions: Set<Int>, blankInput: String): Boolean {
        return when (question.type) {
            QuestionType.SINGLE -> {
                if (selectedOptions.size != 1) false
                else {
                    val answerIndex = question.options.indexOf(question.answers.firstOrNull())
                    selectedOptions.first() == answerIndex
                }
            }

            QuestionType.MULTI -> {
                val answerIndexes = question.answers.mapNotNull { ans ->
                    question.options.indexOf(ans).takeIf { it >= 0 }
                }.toSet()
                selectedOptions == answerIndexes
            }

            QuestionType.BLANK -> {
                val normalizedInput = normalize(blankInput)
                question.answers.any { normalize(it) == normalizedInput }
            }
        }
    }

    private fun normalize(text: String): String =
        text.lowercase().replace(" ", "").replace("，", ",").replace("。", ".").trim()
}
