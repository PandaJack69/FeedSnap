package com.example.feedsnap.domain.usecase

import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository

class SaveNutritionDataUseCase(
    private val repository: NutritionRepository
) {
    suspend operator fun invoke(nutritionInfo: NutritionInfo) {
        repository.saveNutritionData(nutritionInfo)
    }
}