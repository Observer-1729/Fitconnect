package com.example.fitconnect

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem)

    @Delete
    suspend fun deleteTask(task: TaskItem)

    @Update
    suspend fun updateTask(task: TaskItem)

    @Query("DELETE FROM tasks")
    suspend fun clearAll()

}