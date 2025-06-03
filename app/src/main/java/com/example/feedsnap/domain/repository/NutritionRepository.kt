// NutritionRepository.kt
package com.example.feedsnap.domain.repository

import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.domain.model.NutritionInfo
import kotlinx.coroutines.flow.Flow

//interface NutritionRepository {
//    suspend fun analyzeFoodImage(image: String): NutritionInfo
//    suspend fun saveNutritionData(nutritionInfo: NutritionInfo)
//    fun getNutritionHistory(): Flow<List<NutritionEntity>>
//    suspend fun saveToMealPlan(nutritionInfo: NutritionInfo)
//}

interface NutritionRepository {
    suspend fun analyzeFoodImageWithRoboflow(
        base64Image: String,
        model: String,
        version: String,
        apiKey: String
    ): NutritionInfo
    suspend fun saveNutritionData(nutritionInfo: NutritionInfo)
    fun getNutritionHistory(): Flow<List<NutritionEntity>>
    suspend fun saveToMealPlan(nutritionInfo: NutritionInfo)
}

