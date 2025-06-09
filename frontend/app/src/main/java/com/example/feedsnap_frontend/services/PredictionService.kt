package com.example.feedsnap_frontend.services

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Headers

interface PredictionService {
    @Multipart
    @POST("/predict")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<PredictionResponse> // Change to custom response type
}

// Add this data class
data class PredictionResponse(
    val prediction: String
)

//interface PredictionService {
//    @Multipart
//    @POST("/predict")
//    fun uploadImage(
//        @Part image: MultipartBody.Part
//    ): Call<ResponseBody>
//}
