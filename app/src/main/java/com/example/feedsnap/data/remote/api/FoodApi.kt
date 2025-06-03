package com.example.feedsnap.data.remote.api

import com.example.feedsnap.data.remote.model.Base64ImageRequest
import com.example.feedsnap.data.remote.model.PredictionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query

//interface FoodApi {
//    @POST("analyze")
//    suspend fun analyzeFoodImage(
//        @Body request: Base64ImageRequest
//    ): Response<NutritionResponse>
//}

interface FoodApi {
    @POST("analyze")
    suspend fun analyzeFoodImage(
        @Body request: Base64ImageRequest
    ): Response<PredictionResponse> // ← not NutritionResponse

    @POST("roboflow/{model}/{version}")
    suspend fun analyzeFoodImageWithBase64(
        @Path("model") model: String,
        @Path("version") version: String,
        @Query("api_key") apiKey: String,
        @Body image: Base64ImageRequest
    ): Response<NutritionResponse>
}

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