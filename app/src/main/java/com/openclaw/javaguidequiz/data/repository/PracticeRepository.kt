package com.openclaw.javaguidesquiz.data.repository

import com.openclaw.javaguidesquiz.data.local.FavoriteEntity
import com.openclaw.javaguidesquiz.data.local.QuizDao
import com.openclaw.javaguidesquiz.data.local.WrongBookEntity

class PracticeRepository(private val dao: QuizDao) {
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
}
