package com.example.fitconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity) // Inserts new user or updates existing user

    @Update
    suspend fun updateUser(user: UserEntity) // Updates user data

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUser(userId: String): UserEntity? // Fetches user by ID

    @Query("DELETE FROM user_table WHERE id = :userId")
    suspend fun deleteUser(userId: String) // Deletes specific user

    @Query("DELETE FROM user_table")
    suspend fun clearUsers() // Deletes all users
}