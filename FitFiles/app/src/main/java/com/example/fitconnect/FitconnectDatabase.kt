package com.example.fitconnect

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, TaskItem::class, DietItem::class, ExerciseItem::class, ProfileEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao // This function gives us access to the DAO
    abstract fun taskDao(): TaskDao
    abstract fun dietDao(): DietDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {  // Ensures only one instance is created
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitconnect_database" // Name of the database
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}