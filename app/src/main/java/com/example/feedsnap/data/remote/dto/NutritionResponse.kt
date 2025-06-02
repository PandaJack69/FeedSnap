package com.example.feedsnap.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutritionResponse(
    @SerialName("food_name")
    val foodName: String,
    @SerialName("calories")
    val calories: Float,
    @SerialName("nutrients")
    val nutrients: Map<String, Float>,
    @SerialName("serving_size")
    val servingSize: String,
    @SerialName("confidence")
    val confidence: Float
)