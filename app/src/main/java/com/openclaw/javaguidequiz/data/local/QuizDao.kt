package com.openclaw.javaguidesquiz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {
    @Query("SELECT * FROM question LIMIT :limit")
    suspend fun getQuestions(limit: Int = 50): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuestions(items: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOptions(items: List<QuestionOptionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWrongBook(item: WrongBookEntity)

    @Query("SELECT questionId FROM wrong_book")
    suspend fun getWrongBookIds(): List<String>

    @Query("SELECT questionId FROM favorite")
    suspend fun getFavoriteIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(item: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE questionId = :questionId")
    suspend fun removeFavorite(questionId: String)
}
