package com.example.feedsnap_frontend.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

//    private const val BASE_URL = "http://YOUR_LOCAL_IP:5050"
    private const val BASE_URL = "http://192.168.100.7:5050"

    val instance: PredictionService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PredictionService::class.java)
    }

    val nutritionService: NutritionService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.100.7:8002/")  // Replace with your host machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionService::class.java)
    }

}
