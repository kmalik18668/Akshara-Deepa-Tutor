package com.aksharadeeptutor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: Int = 0,
    val progress: Int = 0,
    val totalChapters: Int = 0
)
