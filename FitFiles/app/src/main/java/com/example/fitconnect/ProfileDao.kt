package com.example.fitconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile_table WHERE uid = :uid")
    suspend fun getProfile(uid: String): ProfileEntity?

    @Query("DELETE FROM profile_table")
    suspend fun clearProfile() // Optional, for logout or reset
}
