package com.example.feedsnap.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.feedsnap.data.local.dao.NutritionDao
import com.example.feedsnap.data.local.entities.NutritionEntity

@Database(
    entities = [NutritionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nutritionDao(): NutritionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "feed_snap_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}