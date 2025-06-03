package com.example.feedsnap.data.repository

import com.example.feedsnap.data.local.dao.NutritionDao
import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.data.remote.api.FoodApi
//import com.example.feedsnap.data.remote.api.NutritionResponse
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import com.example.feedsnap.data.remote.dto.NutritionResponse
import com.example.feedsnap.data.remote.model.Base64ImageRequest
import com.example.feedsnap.domain.model.toNutritionInfo

class NutritionRepositoryImpl(
    private val foodApi: FoodApi,
    private val nutritionDao: NutritionDao,
    private val gson: Gson = Gson()
) : NutritionRepository {

    override suspend fun analyzeFoodImageWithRoboflow(
        base64Image: String,
        model: String,
        version: String,
        apiKey: String
    ): NutritionInfo {
        val imageWithPrefix = "data:image/jpeg;base64,$base64Image"
        val request = Base64ImageRequest(image = imageWithPrefix)
        val response = foodApi.analyzeFoodImageWithBase64(model, version, apiKey, request)
//        val response = foodApi.analyzeFoodImageWithBase64(model, version, apiKey, request)

        if (response.isSuccessful) {
            return response.body()?.toNutritionInfo()
                ?: throw Exception("Empty response body")
        } else {
            throw Exception("API call failed: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun saveNutritionData(nutritionInfo: NutritionInfo) {
        nutritionDao.insertNutrition(nutritionInfo.toEntity())
    }

    override fun getNutritionHistory(): Flow<List<NutritionEntity>> {
        return nutritionDao.getNutritionHistory()
    }

    override suspend fun saveToMealPlan(nutritionInfo: NutritionInfo) {
        nutritionDao.insertNutrition(nutritionInfo.toEntity())
    }

    // Helper
    private fun NutritionResponse.toNutritionInfo(): NutritionInfo {
        return NutritionInfo(
            foodName = foodName,
            calories = calories,
            nutrients = nutrients,
            servingSize = servingSize,
            confidence = confidence
        )
    }

    private fun parseNutrients(json: String): Map<String, Float> {
        val type = object : TypeToken<Map<String, Float>>() {}.type
        return gson.fromJson(json, type) ?: emptyMap()
    }
}
