package com.aksharadeeptutor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttempt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long,
    val answers: String
)
