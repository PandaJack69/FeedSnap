package com.example.feedsnap.domain.model

import android.os.Parcelable
import com.example.feedsnap.data.local.entities.NutritionEntity
import com.example.feedsnap.data.remote.api.NutritionResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.json.Json

@Parcelize
data class NutritionInfo(
    val foodName: String,
    val calories: Float,
    val nutrients: Map<String, Float>,
    val servingSize: String,
    val confidence: Float = 0f,
    val date: Long = System.currentTimeMillis()
) : Parcelable {
    fun toEntity(): NutritionEntity {
        return NutritionEntity(
            foodName = foodName,
            calories = calories,
            nutrientsJson = Gson().toJson(nutrients)
        )
    }
}

//fun NutritionEntity.toNutritionInfo(): NutritionInfo {
//    return NutritionInfo(
//        foodName = foodName,
//        calories = calories,
//        nutrients = Gson().fromJson(nutrientsJson, object : TypeToken<Map<String, Float>>() {}.type),
//        servingSize = "",
//        date = date
//    )
//}

fun NutritionEntity.toNutritionInfo(): NutritionInfo {
    return NutritionInfo(
        foodName = this.foodName,
        calories = this.calories,
        nutrients = parseNutrients(this.nutrientsJson), // Implement this
        servingSize = "",
        confidence = 0f,
        date = this.date
    )
}

fun parseNutrients(json: String): Map<String, Float> {
    // Implement your JSON parsing logic here
    // Example using kotlinx.serialization:
    return Json.decodeFromString(json)
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