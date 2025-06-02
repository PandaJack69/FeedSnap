package com.example.feedsnap.domain.usecase

import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GetNutritionHistoryUseCase(
    private val repository: NutritionRepository
) {
    operator fun invoke(): Flow<List<NutritionInfo>> {
        return repository.getNutritionHistory().map { entities ->
            entities.map { entity ->
                NutritionInfo(
                    foodName = entity.foodName,
                    calories = entity.calories,
                    nutrients = Json.decodeFromString(entity.nutrientsJson),
                    servingSize = "",
                    confidence = 0f,
                    date = entity.date
                )
            }
        }
    }
}