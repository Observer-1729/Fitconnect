package com.example.fitconnect

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exercise_items")
data class ExerciseItem(
    @PrimaryKey val id: String,
    val exercise:String,
    val description:String,
    val gif:String,
    @SerializedName("reps/time") val repsTime: String,
    val effects:String
)
