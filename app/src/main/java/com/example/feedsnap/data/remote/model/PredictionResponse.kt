package com.example.feedsnap.data.remote.model
import kotlinx.serialization.Serializable

@Serializable
data class PredictionResponse(
    val foodName: String,
    val confidence: Float = 0f // optional for now
)
//data class PredictionResponse(
//    val class_name: String,
//    val confidence: Double
//)



