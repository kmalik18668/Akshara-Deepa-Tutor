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
import com.aksharadeeptutor.ui.syllabus.SubjectUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TutorViewModel(private val repository: TutorRepository) : ViewModel() {

    val subjects: Flow<List<Subject>> = repository.subjects

    private val _selectedSubjectId = MutableStateFlow<Int?>(null)
    val selectedSubjectId: StateFlow<Int?> = _selectedSubjectId

    /**
     * A single combined flow emitting the full syllabus UI list.
     * Uses flatMapLatest + combine to avoid nested Flow collectors.
     */
    val syllabusUiState: Flow<List<SubjectUiModel>> = repository.subjects.flatMapLatest { subjects ->
        if (subjects.isEmpty()) return@flatMapLatest flowOf(emptyList())

        val chapterFlows: List<Flow<Pair<Subject, List<Chapter>>>> = subjects.map { subject ->
            repository.getChaptersBySubject(subject.id).map { chapters ->
                subject to chapters
            }
        }

        combine(chapterFlows) { pairsArray ->
            pairsArray.map { (subject, chapters) ->
                val completed = chapters.count { it.status == ChapterStatus.COMPLETED }
                val progress = if (chapters.isNotEmpty()) (completed * 100) / chapters.size else 0
                SubjectUiModel(
                    subject = subject.copy(progress = progress),
                    chapters = chapters,
                    isExpanded = false
                )
            }
        }
    }

    /**
     * Overall progress flow: (totalCompleted, totalChapters) across all subjects.
     */
    val totalProgressFlow: Flow<Pair<Int, Int>> = repository.subjects.flatMapLatest { subjects ->
        if (subjects.isEmpty()) return@flatMapLatest flowOf(0 to 0)

        val countFlows: List<Flow<Pair<Int, Int>>> = subjects.map { subject ->
            combine(
                repository.getCompletedChaptersCount(subject.id),
                repository.getTotalChaptersCount(subject.id)
            ) { completed, total -> completed to total }
        }

        combine(countFlows) { pairs ->
            val totalCompleted = pairs.sumOf { it.first }
            val totalAll = pairs.sumOf { it.second }
            totalCompleted to totalAll
        }
    }

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

    fun markChapterNotStarted(chapterId: Int) = viewModelScope.launch {
        repository.updateChapterStatus(chapterId, ChapterStatus.NOT_STARTED, 0)
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

    /**
     * Returns subjects with mastery below 60% as "Gap Areas".
     * Returns a list of Pair(subjectName, masteryPercent).
     */
    suspend fun getGapAreas(): List<Pair<String, Int>> {
        val subjectList = repository.getAllSubjects()
        return subjectList.mapNotNull { subject ->
            val mastery = repository.getAverageScoreForSubject(subject.id) ?: 0.0
            val pct = (mastery * 100).toInt()
            if (pct < 60) subject.name to pct else null
        }
    }

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
