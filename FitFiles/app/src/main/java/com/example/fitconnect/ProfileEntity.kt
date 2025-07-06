package com.example.fitconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey val uid: String,
    val imageUri: String? = null
)
