package com.example.feedsnap.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import kotlinx.serialization.Serializable

interface FoodApi {
    @POST("analyze")
    suspend fun analyzeFoodImage(
        @Body request: AnalyzeImageRequest
    ): Response<NutritionResponse>  // Using Response wrapper for better error handling

    @GET("food/{id}")
    suspend fun getFoodDetails(
        @Path("id") foodId: String
    ): Response<FoodDetailsResponse>
}

//@Serializable
//data class AnalyzeImageRequest(
//    val image: String,
//    val model: String = "food-101"
//)
//
//@Serializable
//data class NutritionResponse(
//    val foodName: String,
//    val calories: Float,
//    val nutrients: Map<String, Float>,
//    val servingSize: String,
//    val confidence: Float
//)
//
//@Serializable
//data class FoodDetailsResponse(
//    val id: String,
//    val name: String,
//    val description: String,
//    val nutritionInfo: NutritionResponse,
//    val images: List<String> = emptyList()
//)

// Request/Response data classes
@Serializable
data class AnalyzeImageRequest(
    val image: String, // Base64
    val model: String = "food-101"
)

@Serializable
data class NutritionResponse(
    val foodName: String,
    val calories: Float,
    val nutrients: Map<String, Float>,
    val servingSize: String,
    val confidence: Float
)

@Serializable
data class FoodDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val nutritionInfo: NutritionResponse,
    val images: List<String> = emptyList()
)