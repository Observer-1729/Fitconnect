package com.example.fitconnect

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "diet_items")
data class DietItem(
    @PrimaryKey val id: String,
    val name: String,
    val image: String,
    val amount: String,
    val recipe1: String,
    val recipe1Description: String,
    val recipe2: String,
    val recipe2Description: String,
    val recipe3: String,
    val recipe3Description: String
)
