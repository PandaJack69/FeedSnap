package com.example.feedsnap.di

import com.example.feedsnap.data.local.AppDatabase
import com.example.feedsnap.data.remote.RetrofitClient
import com.example.feedsnap.data.repository.NutritionRepositoryImpl
import com.example.feedsnap.domain.repository.NutritionRepository
import com.example.feedsnap.domain.usecase.GetNutritionDataUseCase
import com.example.feedsnap.domain.usecase.GetNutritionHistoryUseCase
import com.example.feedsnap.domain.usecase.SaveNutritionDataUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.lifecycle.ViewModel
import com.example.feedsnap.domain.usecase.SaveToMealPlanUseCase
import com.example.feedsnap.features.bmi.BMIViewModel
import com.example.feedsnap.features.foodscan.FoodScanViewModel
import com.example.feedsnap.features.nutrition.NutritionViewModel

object AppModule {

    val module = module {
        // Database
        single { AppDatabase.getDatabase(androidContext()) }
        single { get<AppDatabase>().nutritionDao() }

        // Remote Data
        single { RetrofitClient.foodApi }

        single<NutritionRepository> {
            NutritionRepositoryImpl(
                foodApi = get(),
                nutritionDao = get()
            )
        }

        // Use Cases
        factory { GetNutritionDataUseCase(get()) }
        factory { SaveNutritionDataUseCase(get()) }
        factory { GetNutritionHistoryUseCase(get()) }
        factory { SaveToMealPlanUseCase(get()) }

        // ViewModels
        viewModel {
            FoodScanViewModel(
                getNutritionDataUseCase = get(),
                saveNutritionDataUseCase = get(),
                repo = get()
            )
        }
        viewModel<NutritionViewModel> { NutritionViewModel(get()) }
        viewModel { BMIViewModel() }
    }
}