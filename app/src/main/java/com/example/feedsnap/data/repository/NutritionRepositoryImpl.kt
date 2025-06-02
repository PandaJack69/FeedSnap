package com.example.feedsnap.data.repository

import com.example.feedsnap.data.local.dao.NutritionDao
import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.data.remote.api.FoodApi
import com.example.feedsnap.data.remote.api.AnalyzeImageRequest
import com.example.feedsnap.data.remote.api.NutritionResponse
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class NutritionRepositoryImpl(
    private val foodApi: FoodApi,
    private val nutritionDao: NutritionDao
) : NutritionRepository {
    override suspend fun analyzeFoodImage(image: String): NutritionInfo {
        val response: Response<NutritionResponse> = foodApi.analyzeFoodImage(AnalyzeImageRequest(image))
        if (!response.isSuccessful || response.body() == null) {
            throw Exception("API request failed: ${response.code()} - ${response.message()}")
        }
        return response.body()!!.toNutritionInfo()
    }

    override suspend fun saveNutritionData(nutritionInfo: NutritionInfo) {
        nutritionDao.insertNutrition(nutritionInfo.toEntity())
    }

//    override fun getNutritionHistory(): Flow<List<NutritionInfo>> {
//        return nutritionDao.getNutritionHistory().map { entities ->
//            entities.map { entity -> entity.toNutritionInfo() }
//        }
//    }
    override fun getNutritionHistory(): Flow<List<NutritionEntity>> {
        return nutritionDao.getNutritionHistory()
    }


    override suspend fun saveToMealPlan(nutritionInfo: NutritionInfo) {
        nutritionDao.insertNutrition(nutritionInfo.toEntity())
    }

    // Extension functions for conversion
    private fun NutritionResponse.toNutritionInfo(): NutritionInfo {
        return NutritionInfo(
            foodName = this.foodName,
            calories = this.calories,
            nutrients = this.nutrients,
            servingSize = this.servingSize,
            confidence = this.confidence
        )
    }

    private fun NutritionInfo.toEntity(): NutritionEntity {
        return NutritionEntity(
            foodName = this.foodName,
            calories = this.calories,
            nutrientsJson = this.nutrients.toString(), // You might want proper JSON serialization here
            date = System.currentTimeMillis()
        )
    }

    private fun NutritionEntity.toNutritionInfo(): NutritionInfo {
        return NutritionInfo(
            foodName = this.foodName,
            calories = this.calories,
            nutrients = parseNutrients(this.nutrientsJson), // You'll need to implement this
            servingSize = "",
            confidence = 0f,
            date = this.date
        )
    }

    private fun parseNutrients(json: String): Map<String, Float> {
        // Implement proper JSON parsing here
        return emptyMap()
    }
}