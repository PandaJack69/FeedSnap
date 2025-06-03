package com.example.feedsnap.domain.usecase

import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository

class GetNutritionDataUseCase(
    private val repository: NutritionRepository
) {
//    suspend operator fun invoke(image: String): NutritionInfo {
//        return repository.analyzeFoodImage(image)
//    }
}