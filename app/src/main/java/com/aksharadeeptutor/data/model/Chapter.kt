package com.aksharadeeptutor.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "chapters",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["subjectId"])]
)
data class Chapter(
    @PrimaryKey val id: Int,
    val subjectId: Int,
    val name: String,
    val status: ChapterStatus = ChapterStatus.NOT_STARTED,
    val progress: Int = 0
)

enum class ChapterStatus {
    NOT_STARTED, IN_PROGRESS, COMPLETED
}
