package com.example.feedsnap.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.feedsnap.domain.model.NutritionInfo
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")

//    object FoodScan : Screen("food_scan/{imageUri}") {
//        fun createRoute(imageUri: Uri): String = "food_scan/${Uri.encode(imageUri.toString())}"
//    }
    object FoodScan : Screen("foodscan/{imageUri}") {
        fun createRoute(imageUri: String): String = "foodscan/${Uri.encode(imageUri)}"
    }


    object BMI : Screen("bmi")

    object NutritionDetail : Screen("nutrition_detail/{nutritionInfo}") {
        fun createRoute(nutritionInfo: NutritionInfo) =
            "nutrition_detail/${Uri.encode(Gson().toJson(nutritionInfo))}"
    }

    object Camera : Screen("camera")
    object Gallery : Screen("gallery")
}

class NutritionInfoType : NavType<NutritionInfo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): NutritionInfo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): NutritionInfo {
        return Gson().fromJson(value, NutritionInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: NutritionInfo) {
        bundle.putParcelable(key, value)
    }
}