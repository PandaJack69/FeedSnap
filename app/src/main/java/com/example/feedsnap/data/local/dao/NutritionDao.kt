package com.example.feedsnap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.feedsnap.data.local.entities.NutritionEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface NutritionDao {
    @Insert
    suspend fun insertNutrition(nutrition: NutritionEntity)

    @Query("SELECT * FROM nutrition_history ORDER BY date DESC")
    fun getNutritionHistory(): Flow<List<NutritionEntity>>

    @Query("DELETE FROM nutrition_history WHERE id = :id")
    suspend fun deleteNutrition(id: Long)
}