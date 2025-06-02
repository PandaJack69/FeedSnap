package com.example.feedsnap.features.foodscan

import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository
import com.example.feedsnap.domain.usecase.GetNutritionDataUseCase
import com.example.feedsnap.domain.usecase.SaveNutritionDataUseCase
import com.example.feedsnap.utils.ImageUtils
import kotlinx.coroutines.launch

sealed class FoodScanState {
    object Idle : FoodScanState()
    object Loading : FoodScanState()
    data class Success(val data: NutritionInfo) : FoodScanState()
    data class Error(val message: String) : FoodScanState()
}

class FoodScanViewModel(
    private val getNutritionDataUseCase: GetNutritionDataUseCase,
    private val saveNutritionDataUseCase: SaveNutritionDataUseCase,
    private val repo: NutritionRepository
) : ViewModel() {
    private val _uiState = mutableStateOf<FoodScanState>(FoodScanState.Idle)
    val uiState: State<FoodScanState> = _uiState

    var selectedImage: String? = null

    fun analyzeFoodImage(image: ImageProxy) {
        viewModelScope.launch {
            _uiState.value = FoodScanState.Loading
            try {
                val base64Image = ImageUtils.imageProxyToBase64(image)
                val result = getNutritionDataUseCase(base64Image)
//                val result = getNutritionDataUseCase(image) // Use the use case instead of repo directly
                _uiState.value = FoodScanState.Success(result)
            } catch (e: Exception) {
                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun saveNutritionData() {
        (uiState.value as? FoodScanState.Success)?.let { state ->
            viewModelScope.launch {
                saveNutritionDataUseCase(state.data)
            }
        }
    }

    fun resetState() {
        _uiState.value = FoodScanState.Idle
        selectedImage = null
    }

    fun analyzeCameraImage(image: ImageProxy) {
        viewModelScope.launch {
            _uiState.value = FoodScanState.Loading
            try {
                val base64Image = ImageUtils.imageProxyToBase64(image)
                val result = getNutritionDataUseCase(base64Image)
                _uiState.value = FoodScanState.Success(result)
            } catch (e: Exception) {
                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun analyzeBase64Image(image: String) {
        viewModelScope.launch {
            _uiState.value = FoodScanState.Loading
            try {
                val result = repo.analyzeFoodImage(image)
                _uiState.value = FoodScanState.Success(result)
            } catch (e: Exception) {
                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
            }
        }
    }

//    fun setSelectedImage(base64Image: String) {
//        selectedImage = base64Image
//        selectedImage?.let { analyzeBase64Image(it) }
//    }

    fun analyzeFoodImage(image: String) {
        viewModelScope.launch {
            _uiState.value = FoodScanState.Loading
            try {
                val result = repo.analyzeFoodImage(image)
                _uiState.value = FoodScanState.Success(result)
            } catch (e: Exception) {
                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
            }
        }
    }
}