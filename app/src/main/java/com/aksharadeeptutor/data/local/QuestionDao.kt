package com.aksharadeeptutor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aksharadeeptutor.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE chapterId = :chapterId ORDER BY RANDOM() LIMIT 5")
    suspend fun getQuestionsForChapter(chapterId: Int): List<Question>

    @Query("SELECT * FROM questions WHERE chapterId = :chapterId")
    fun getAllQuestionsForChapter(chapterId: Int): Flow<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getTotalQuestionCount(): Int
}
