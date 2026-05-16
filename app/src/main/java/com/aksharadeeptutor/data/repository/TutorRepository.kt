package com.aksharadeeptutor.data.repository

import com.aksharadeeptutor.data.local.AppDatabase
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import com.aksharadeeptutor.data.model.Question
import com.aksharadeeptutor.data.model.QuizAttempt
import com.aksharadeeptutor.data.model.Subject
import kotlinx.coroutines.flow.Flow

class TutorRepository(private val db: AppDatabase) {

    val subjects: Flow<List<Subject>> = db.subjectDao().getAllSubjects()

    fun getChaptersBySubject(subjectId: Int): Flow<List<Chapter>> =
        db.chapterDao().getChaptersBySubject(subjectId)

    fun getCompletedChaptersCount(subjectId: Int): Flow<Int> =
        db.chapterDao().getCompletedChaptersCount(subjectId)

    fun getTotalChaptersCount(subjectId: Int): Flow<Int> =
        db.chapterDao().getTotalChaptersCount(subjectId)

    suspend fun getQuestionsForChapter(chapterId: Int): List<Question> =
        db.questionDao().getQuestionsForChapter(chapterId)

    suspend fun saveQuizAttempt(quizAttempt: QuizAttempt): Long =
        db.quizAttemptDao().insertQuizAttempt(quizAttempt)

    suspend fun updateChapterStatus(chapterId: Int, status: ChapterStatus, progress: Int) =
        db.chapterDao().updateChapterStatus(chapterId, status, progress)

    suspend fun getBestScoreForChapter(chapterId: Int): Double? =
        db.quizAttemptDao().getBestScoreForChapter(chapterId)

    suspend fun getAverageScoreForSubject(subjectId: Int): Double? =
        db.quizAttemptDao().getAverageScoreForSubject(subjectId)

    suspend fun getAllAttempts(): List<QuizAttempt> =
        db.quizAttemptDao().getAllAttempts()
}
