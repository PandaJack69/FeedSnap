package com.example.feedsnap_frontend.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class Nutrient(
    val name: String,
    val value: Double,
    val unit: String,
    val percent: Double
)

data class NutritionResponse(
    val status: String,
    val data: List<Nutrient>?
)

interface NutritionService {
    @GET("nutrients")
    fun getNutritionInfo(
        @Query("food") food: String,
        @Query("amount") amount: Int = 100
    ): Call<NutritionResponse>
}
