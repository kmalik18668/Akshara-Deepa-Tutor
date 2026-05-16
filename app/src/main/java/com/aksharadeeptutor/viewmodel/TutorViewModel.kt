package com.aksharadeeptutor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aksharadeeptutor.data.local.AppDatabase
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import com.aksharadeeptutor.data.model.Question
import com.aksharadeeptutor.data.model.QuizAttempt
import com.aksharadeeptutor.data.model.Subject
import com.aksharadeeptutor.data.repository.TutorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TutorViewModel(private val repository: TutorRepository) : ViewModel() {

    val subjects: Flow<List<Subject>> = repository.subjects

    private val _selectedSubjectId = MutableStateFlow<Int?>(null)
    val selectedSubjectId: StateFlow<Int?> = _selectedSubjectId

    fun getChaptersForSubject(subjectId: Int): Flow<List<Chapter>> =
        repository.getChaptersBySubject(subjectId)

    fun getCompletedChaptersCount(subjectId: Int): Flow<Int> =
        repository.getCompletedChaptersCount(subjectId)

    fun getTotalChaptersCount(subjectId: Int): Flow<Int> =
        repository.getTotalChaptersCount(subjectId)

    fun getProgressData(subjectId: Int): Flow<ProgressData> = combine(
        repository.getCompletedChaptersCount(subjectId),
        repository.getTotalChaptersCount(subjectId)
    ) { completed, total ->
        ProgressData(completed, total, if (total > 0) (completed * 100 / total) else 0)
    }

    fun selectSubject(subjectId: Int) {
        _selectedSubjectId.value = subjectId
    }

    fun markChapterComplete(chapterId: Int) = viewModelScope.launch {
        repository.updateChapterStatus(chapterId, ChapterStatus.COMPLETED, 100)
    }

    fun markChapterInProgress(chapterId: Int) = viewModelScope.launch {
        repository.updateChapterStatus(chapterId, ChapterStatus.IN_PROGRESS, 50)
    }

    suspend fun getQuizQuestions(chapterId: Int): List<Question> =
        repository.getQuestionsForChapter(chapterId)

    fun submitQuiz(chapterId: Int, score: Int, totalQuestions: Int, answers: String) = viewModelScope.launch {
        val attempt = QuizAttempt(
            chapterId = chapterId,
            score = score,
            totalQuestions = totalQuestions,
            timestamp = System.currentTimeMillis(),
            answers = answers
        )
        repository.saveQuizAttempt(attempt)

        if (score >= totalQuestions * 0.6) {
            repository.updateChapterStatus(chapterId, ChapterStatus.COMPLETED, 100)
        } else {
            repository.updateChapterStatus(chapterId, ChapterStatus.IN_PROGRESS, 50)
        }
    }

    suspend fun getSubjectMastery(subjectId: Int): Double =
        repository.getAverageScoreForSubject(subjectId) ?: 0.0

    data class ProgressData(
        val completed: Int,
        val total: Int,
        val percentage: Int
    )
}

class TutorViewModelFactory(private val db: AppDatabase) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TutorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TutorViewModel(TutorRepository(db)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
