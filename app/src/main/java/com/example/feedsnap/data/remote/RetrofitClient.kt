package com.example.feedsnap.data.remote

import com.example.feedsnap.data.remote.api.FoodApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    // Add to your Retrofit client setup (RetrofitClient.kt):
//    val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//        .build()

    private const val BASE_URL = "https://serverless.roboflow.com/" // Replace with your actual base URL

    // Create JSON instance with any custom configuration
    private val json = Json {
        ignoreUnknownKeys = true // Ignore unknown JSON keys
        coerceInputValues = true // Coerce null values to defaults
    }

    // Create OkHttpClient with logging interceptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request/response bodies
        })
        .build()

    // Retrofit instance
    val foodApi: FoodApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(FoodApi::class.java)
    }
}