package com.example.fitconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey val id: Int,
    val title: String,
    val time: String,
    val timeInMillis: Long,
    val completed: Boolean = false,
    val notified: Boolean = false
)
