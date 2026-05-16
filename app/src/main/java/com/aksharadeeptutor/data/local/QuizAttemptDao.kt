package com.aksharadeeptutor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aksharadeeptutor.data.model.QuizAttempt
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {
    @Insert
    suspend fun insertQuizAttempt(quizAttempt: QuizAttempt): Long

    @Query("SELECT * FROM quiz_attempts WHERE chapterId = :chapterId ORDER BY timestamp DESC")
    fun getAttemptsByChapter(chapterId: Int): Flow<List<QuizAttempt>>

    @Query("SELECT * FROM quiz_attempts ORDER BY timestamp DESC")
    fun getAllAttempts(): Flow<List<QuizAttempt>>

    @Query("SELECT MAX(score * 1.0 / totalQuestions) FROM quiz_attempts WHERE chapterId = :chapterId")
    suspend fun getBestScoreForChapter(chapterId: Int): Double?

    @Query("SELECT AVG(score * 1.0 / totalQuestions) FROM quiz_attempts WHERE chapterId IN (SELECT id FROM chapters WHERE subjectId = :subjectId)")
    suspend fun getAverageScoreForSubject(subjectId: Int): Double?
}
