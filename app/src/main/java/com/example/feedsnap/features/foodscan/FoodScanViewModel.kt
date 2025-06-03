package com.example.feedsnap.features.foodscan

import androidx.camera.core.ImageProxy
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedsnap.data.remote.RetrofitClient.foodApi
import com.example.feedsnap.data.remote.model.Base64ImageRequest
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.domain.model.toNutritionInfo
import com.example.feedsnap.domain.repository.NutritionRepository
import com.example.feedsnap.domain.usecase.GetNutritionDataUseCase
import com.example.feedsnap.domain.usecase.SaveNutritionDataUseCase
import com.example.feedsnap.utils.ImageUtils
//import com.github.mikephil.charting.BuildConfig
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

//import com.example.feedsnap.BuildConfig

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

//    fun analyzeFoodImage(image: ImageProxy) {
//        viewModelScope.launch {
//            _uiState.value = FoodScanState.Loading
//            try {
//                val base64Image = ImageUtils.imageProxyToBase64(image)
//                val result = getNutritionDataUseCase(base64Image)
////                val result = getNutritionDataUseCase(image) // Use the use case instead of repo directly
//                _uiState.value = FoodScanState.Success(result)
//            } catch (e: Exception) {
//                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

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

//    fun analyzeFoodImage(file: File) {
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)
//
//        viewModelScope.launch {
//            try {
//                val result = foodApi.analyzeFoodImage(multipart)
//                _uiState.value = FoodScanState.Success(result.body())
//            } catch (e: Exception) {
//                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

//    fun analyzeFoodImage(file: File) {
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)
//
//        viewModelScope.launch {
//            try {
//                val result = foodApi.analyzeFoodImage(multipart)
//                _uiState.value = FoodScanState.Success(result.body())
//            } catch (e: Exception) {
//                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }

    fun analyzeFoodImage(file: File) {
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val base64Image = file.readBytes().encodeBase64()
        val imageWithPrefix = "data:image/jpeg;base64,$base64Image"
        val request = Base64ImageRequest(image = imageWithPrefix)

        viewModelScope.launch {
            try {
//                val result = foodApi.analyzeFoodImage(multipart)
                val result = foodApi.analyzeFoodImage(request)

//                val prediction = result.body()
//                if (prediction != null) {
//                    val nutritionInfo = prediction.toNutritionInfo()
//                    _uiState.value = FoodScanState.Success(nutritionInfo)
//                } else {
//                    _uiState.value = FoodScanState.Error("Empty response from API")
//                }
//                val prediction = result.body()
//                if (prediction != null) {
//                    _uiState.value = FoodScanState.Success(foodName = prediction.foodName)
//                } else {
//                    _uiState.value = FoodScanState.Error("Empty response from API")
//                }

                val prediction = result.body()
                if (prediction != null) {
                    val nutritionInfo = prediction.toNutritionInfo()
                    _uiState.value = FoodScanState.Success(nutritionInfo)
                } else {
                    _uiState.value = FoodScanState.Error("Empty response from API")
                }


            } catch (e: Exception) {
                _uiState.value = FoodScanState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun ByteArray.encodeBase64(): String =
        android.util.Base64.encodeToString(this, android.util.Base64.DEFAULT)

}