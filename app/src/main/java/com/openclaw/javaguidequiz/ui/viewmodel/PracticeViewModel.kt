package com.openclaw.javaguidesquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.openclaw.javaguidesquiz.data.repository.SampleQuestionRepository
import com.openclaw.javaguidesquiz.domain.model.PracticeMode
import com.openclaw.javaguidesquiz.domain.model.PracticeState
import com.openclaw.javaguidesquiz.domain.model.Question
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import com.openclaw.javaguidesquiz.domain.model.Scoring
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PracticeViewModel : ViewModel() {
    private val all = SampleQuestionRepository.load()

    private val _state = MutableStateFlow(
        PracticeState(allQuestions = all, questions = all)
    )
    val state: StateFlow<PracticeState> = _state

    fun onSelectOption(index: Int) {
        val current = _state.value.current ?: return
        _state.update { s ->
            val next = when (current.type) {
                QuestionType.SINGLE -> setOf(index)
                QuestionType.MULTI -> if (index in s.selectedOptions) s.selectedOptions - index else s.selectedOptions + index
                QuestionType.BLANK -> s.selectedOptions
            }
            s.copy(selectedOptions = next)
        }
    }

    fun onBlankInput(text: String) {
        _state.update { it.copy(blankInput = text) }
    }

    fun submit() {
        val s = _state.value
        val q = s.current ?: return
        val correct = Scoring.check(q, s.selectedOptions, s.blankInput)
        _state.update {
            it.copy(
                showResult = true,
                isCorrect = correct,
                score = it.score + if (correct) 1 else 0,
                wrongBook = if (correct) it.wrongBook else it.wrongBook + q.id
            )
        }
    }

    fun next() {
        _state.update { s ->
            val isLast = s.index >= s.questions.lastIndex
            if (isLast) s.copy(completed = true)
            else s.copy(
                index = s.index + 1,
                selectedOptions = emptySet(),
                blankInput = "",
                showResult = false,
                isCorrect = null
            )
        }
    }

    fun restart() {
        val s = _state.value
        val filtered = filterQuestions(s.selectedCategory, s.mode)
        _state.update {
            it.copy(
                questions = filtered,
                index = 0,
                selectedOptions = emptySet(),
                blankInput = "",
                showResult = false,
                isCorrect = null,
                completed = false,
                score = 0
            )
        }
    }

    fun setCategory(category: String) {
        _state.update { it.copy(selectedCategory = category) }
        restart()
    }

    fun setMode(mode: PracticeMode) {
        _state.update { it.copy(mode = mode) }
        restart()
    }

    fun toggleFavorite() {
        val q = _state.value.current ?: return
        _state.update {
            val fav = if (q.id in it.favorites) it.favorites - q.id else it.favorites + q.id
            it.copy(favorites = fav)
        }
    }

    private fun filterQuestions(category: String, mode: PracticeMode): List<Question> {
        val base = if (category == "全部") all else all.filter { it.category == category }
        return when (mode) {
            PracticeMode.SEQUENTIAL -> base
            PracticeMode.RANDOM -> base.shuffled()
        }
    }
}
