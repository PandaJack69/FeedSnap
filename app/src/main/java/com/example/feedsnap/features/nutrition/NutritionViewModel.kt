package com.example.feedsnap.features.nutrition

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.usecase.SaveToMealPlanUseCase
import kotlinx.coroutines.launch

class NutritionViewModel(
    private val saveToMealPlanUseCase: SaveToMealPlanUseCase
) : ViewModel() {
    private val _state = mutableStateOf<NutritionViewState>(NutritionViewState.Idle)
//    val state: Lifecycle.State<NutritionViewState> = _state
    val state = _state


    fun saveToMealPlan(nutritionInfo: NutritionInfo) {
        viewModelScope.launch {
            _state.value = NutritionViewState.Loading
            try {
                saveToMealPlanUseCase(nutritionInfo)
                _state.value = NutritionViewState.Success("Added to meal plan")
            } catch (e: Exception) {
                _state.value = NutritionViewState.Error(e.message ?: "Failed to save")
            }
        }
    }
}

sealed class NutritionViewState {
    object Idle : NutritionViewState()
    object Loading : NutritionViewState()
    data class Success(val message: String) : NutritionViewState()
    data class Error(val message: String) : NutritionViewState()
}