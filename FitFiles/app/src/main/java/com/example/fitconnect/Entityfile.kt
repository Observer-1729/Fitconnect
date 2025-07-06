package com.example.fitconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")  // Creates a table named "user_table"
data class UserEntity(
    @PrimaryKey val id: String,  // Unique user ID (use Firebase UID or any unique ID)
    val age: Int,
    val gender: String?,  // Nullable field
    val height: Int,
    val weight: Int,
    val bmi: Double,
    val username: String?,
    val goal:String?
)
