package com.example.feedsnap.features.foodscan

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.features.foodscan.components.CameraPreview
import com.example.feedsnap.features.foodscan.components.FullScreenLoader
import com.example.feedsnap.features.foodscan.components.NutritionResultPopup
import com.example.feedsnap.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import com.example.feedsnap.utils.ImageUtils

@Composable
fun FoodScanScreen(
    imageUri: Uri,
    navController: NavController,
    viewModel: FoodScanViewModel = koinViewModel()
//    imageUri: Uri?,
//    viewModel: FoodScanViewModel = koinViewModel(),
//    navController: NavController
) {
    val state = viewModel.uiState.value
    val context = LocalContext.current

    // Automatically analyze the image when this screen is shown
    LaunchedEffect(imageUri) {
//        val base64Image = ImageUtils.uriToBase64(context, imageUri)
//        viewModel.analyzeFoodImage(base64Image)
        val file = ImageUtils.uriToFile(context, imageUri)
        viewModel.analyzeFoodImage(file)

    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state == FoodScanState.Idle) {
            // Placeholder or empty UI
            Box(modifier = Modifier.fillMaxSize())
        }

        when (state) {
            is FoodScanState.Success -> {
                NutritionResultPopup(
                    nutritionInfo = (state as FoodScanState.Success).data,
                    onDismiss = { viewModel.resetState() },
                    onSave = {
                        viewModel.saveNutritionData()
                        navController.navigate(
                            Screen.NutritionDetail.createRoute((state as FoodScanState.Success).data)
                        )
                    }
                )
            }
            is FoodScanState.Loading -> FullScreenLoader()
            is FoodScanState.Error -> ErrorMessage(
                message = (state as FoodScanState.Error).message,
                onRetry = { viewModel.resetState() }
            )
            FoodScanState.Idle -> {}
        }
    }
}

@Composable
fun PermissionDeniedContent(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camera permission required")
        Button(
            onClick = onRequestPermission,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Request Permission")
        }
    }
}

// Add these components if they don't exist

@Composable
fun NutritionResultPopup(
    nutritionInfo: NutritionInfo,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    // Implement your popup UI here
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nutrition Info") },
        text = { Text("Food: ${nutritionInfo.foodName}\nCalories: ${nutritionInfo.calories}") },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun FullScreenLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Try Again")
        }
    }
}