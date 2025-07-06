package com.example.fitconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DietDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dietItems: List<DietItem>)

    @Query("SELECT * FROM diet_items")
    suspend fun getAllDietItems(): List<DietItem>

    @Query("DELETE FROM diet_items")
    suspend fun clearAllDiet()

    @Query("SELECT * FROM diet_items WHERE id = :id")
    fun getDietItemById(id: String): Flow<DietItem?>

}
