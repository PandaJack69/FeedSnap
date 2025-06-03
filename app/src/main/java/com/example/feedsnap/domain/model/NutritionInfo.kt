package com.example.feedsnap.domain.model

import android.os.Parcelable
import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.data.remote.api.NutritionResponse
import com.example.feedsnap.data.remote.model.PredictionResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutritionInfo(
    val foodName: String,
    val calories: Float,
    val nutrients: Map<String, Float>,
    val servingSize: String = "",         // Default used if not available
    val confidence: Float = 0f,           // Default used if not available
    val date: Long = System.currentTimeMillis()
) : Parcelable {
    fun toEntity(): NutritionEntity {
        return NutritionEntity(
            foodName = foodName,
            calories = calories,
            nutrientsJson = Gson().toJson(nutrients),
            date = date
        )
    }
}

// Converts from NutritionEntity (local DB) to NutritionInfo (domain model)
fun NutritionEntity.toNutritionInfo(): NutritionInfo {
    return NutritionInfo(
        foodName = foodName,
        calories = calories,
        nutrients = parseNutrients(nutrientsJson),
        servingSize = "",
        confidence = 0f,
        date = date
    )
}

// Gson used for parsing back
fun parseNutrients(json: String): Map<String, Float> {
    val type = object : TypeToken<Map<String, Float>>() {}.type
    return Gson().fromJson(json, type)
}

fun NutritionResponse.toNutritionInfo(): NutritionInfo {
    return NutritionInfo(
        foodName = foodName,
        calories = calories,
        nutrients = nutrients,
        servingSize = servingSize,
        confidence = confidence
    )
}

//fun PredictionResponse.toNutritionInfo(): NutritionInfo {
//    // Look up nutrition info by class_name (food name) in your database:
//    val foodNameLower = class_name.lowercase()
//    val baseNutrition = nutritionDatabase[foodNameLower]
//
//    return if (baseNutrition != null) {
//        baseNutrition.copy(confidence = confidence.toFloat())
//    } else {
//        // fallback if not found: create minimal NutritionInfo with just name and confidence
//        NutritionInfo(
//            foodName = class_name,
//            calories = 0f,
//            nutrients = emptyMap(),
//            servingSize = "",
//            confidence = confidence.toFloat()
//        )
//    }
//}

//fun PredictionResponse.toNutritionInfo(nutritionDatabase: Map<String, NutritionInfo>): NutritionInfo {
//    val foodNameLower = class_name.lowercase()
//    val baseNutrition = nutritionDatabase[foodNameLower]
//
//    return baseNutrition?.copy(confidence = confidence.toFloat()) ?: NutritionInfo(
//        foodName = class_name,
//        calories = 0f,
//        nutrients = emptyMap(),
//        servingSize = "",
//        confidence = confidence.toFloat()
//    )
//}

fun PredictionResponse.toNutritionInfo(): NutritionInfo {
    return NutritionInfo(
        foodName = foodName,
        calories = 0f,
        nutrients = emptyMap(),
        servingSize = "",
        confidence = confidence
    )
}





