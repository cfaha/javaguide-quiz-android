package com.openclaw.javaguidesquiz.data.repository

import android.content.Context
import com.openclaw.javaguidesquiz.data.local.FavoriteEntity
import com.openclaw.javaguidesquiz.data.local.QuestionEntity
import com.openclaw.javaguidesquiz.data.local.QuestionOptionEntity
import com.openclaw.javaguidesquiz.data.local.QuizDao
import com.openclaw.javaguidesquiz.data.local.WrongBookEntity
import com.openclaw.javaguidesquiz.domain.model.Question
import com.openclaw.javaguidesquiz.domain.model.QuestionType
import org.json.JSONArray

class PracticeRepository(private val dao: QuizDao) {
    suspend fun loadOrSeedQuestions(context: Context): List<Question> {
        val dbQuestions = loadQuestionsFromDb()
        if (dbQuestions.isNotEmpty()) return dbQuestions

        val seed = SampleQuestionRepository.load(context)
        persistQuestions(seed)
        return seed
    }

    suspend fun loadFavoriteIds(): Set<String> = dao.getFavoriteIds().toSet()
    suspend fun loadWrongBookIds(): Set<String> = dao.getWrongBookIds().toSet()

    suspend fun addFavorite(questionId: String) {
        dao.addFavorite(FavoriteEntity(questionId, System.currentTimeMillis()))
    }

    suspend fun removeFavorite(questionId: String) {
        dao.removeFavorite(questionId)
    }

    suspend fun recordWrong(questionId: String) {
        dao.upsertWrongBook(
            WrongBookEntity(
                questionId = questionId,
                wrongCount = 1,
                lastWrongAt = System.currentTimeMillis()
            )
        )
    }

    private suspend fun loadQuestionsFromDb(): List<Question> {
        val questions = dao.getAllQuestions()
        if (questions.isEmpty()) return emptyList()

        val optionMap = dao.getAllOptions().groupBy { it.questionId }
        return questions.map { entity ->
            val options = optionMap[entity.id].orEmpty().sortedBy { it.idx }.map { it.content }
            Question(
                id = entity.id,
                type = entity.type.toQuestionType(),
                category = entity.category,
                stem = entity.stem,
                options = options,
                answers = parseAnswers(entity.answersJson),
                explanation = entity.explanation,
                difficulty = entity.difficulty
            )
        }
    }

    private suspend fun persistQuestions(items: List<Question>) {
        if (items.isEmpty()) return

        val questionEntities = items.map { question ->
            QuestionEntity(
                id = question.id,
                type = question.type.name,
                category = question.category,
                stem = question.stem,
                answersJson = JSONArray(question.answers).toString(),
                explanation = question.explanation,
                difficulty = question.difficulty,
                sourcePath = "assets/questions.json"
            )
        }
        val optionEntities = items.flatMap { question ->
            question.options.mapIndexed { index, option ->
                QuestionOptionEntity(
                    questionId = question.id,
                    idx = index,
                    content = option
                )
            }
        }

        dao.upsertQuestions(questionEntities)
        dao.upsertOptions(optionEntities)
    }

    private fun parseAnswers(json: String): List<String> {
        val arr = JSONArray(json)
        val out = ArrayList<String>(arr.length())
        for (i in 0 until arr.length()) {
            out += arr.optString(i)
        }
        return out
    }

    private fun String.toQuestionType(): QuestionType = when (uppercase()) {
        QuestionType.SINGLE.name -> QuestionType.SINGLE
        QuestionType.MULTI.name -> QuestionType.MULTI
        QuestionType.BLANK.name -> QuestionType.BLANK
        else -> QuestionType.SINGLE
    }
}
