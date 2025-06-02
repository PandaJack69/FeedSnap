package com.example.feedsnap.domain.usecase

import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository

class SaveToMealPlanUseCase(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(info: NutritionInfo) {
        repository.saveToMealPlan(info)
    }
}