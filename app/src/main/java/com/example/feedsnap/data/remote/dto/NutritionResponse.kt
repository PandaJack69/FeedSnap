package com.example.feedsnap.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class NutritionResponse(
    val foodName: String,
    val calories: Float,
    val nutrients: Map<String, Float>,
    val servingSize: String,
    val confidence: Float
)
