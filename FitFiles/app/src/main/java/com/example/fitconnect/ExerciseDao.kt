package com.example.fitconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseItems: List<ExerciseItem>)

    @Query("SELECT * FROM exercise_items")
    suspend fun getAllExerciseItems(): List<ExerciseItem>

    @Query("DELETE FROM exercise_items")
    suspend fun clearAllExercise()

    @Query("SELECT * FROM exercise_items WHERE id = :id")
    fun getExerciseItemById(id: String): Flow<ExerciseItem?>


}
