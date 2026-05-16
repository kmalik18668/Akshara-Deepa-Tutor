package com.aksharadeeptutor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId")
    fun getChaptersBySubject(subjectId: Int): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters")
    fun getAllChapters(): Flow<List<Chapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<Chapter>)

    @Update
    suspend fun updateChapter(chapter: Chapter)

    @Query("UPDATE chapters SET status = :status, progress = :progress WHERE id = :chapterId")
    suspend fun updateChapterStatus(chapterId: Int, status: ChapterStatus, progress: Int)

    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId AND status = 'COMPLETED'")
    fun getCompletedChaptersCount(subjectId: Int): Flow<Int>

    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId = :subjectId")
    fun getTotalChaptersCount(subjectId: Int): Flow<Int>
}
