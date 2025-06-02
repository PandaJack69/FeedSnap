package com.example.feedsnap.features.foodscan

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.feedsnap.features.foodscan.components.CameraPreview
import com.example.feedsnap.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraCaptureScreen(
    navController: NavController,
    viewModel: FoodScanViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cameraPermissionState.status.isGranted) {
            var hasCaptured by remember { mutableStateOf(false) }

            CameraPreview(
//                onImageCaptured = { image ->
//                    if (!hasCaptured) {
//                        hasCaptured = true
//                        viewModel.analyzeFoodImage(image)
//                        navController.navigate(Screen.FoodScan.route) {
//                            popUpTo(Screen.Camera.route) { inclusive = true }
//                        }
//                    }
//                }
                onImageCaptured = { imageUri ->
                    if (!hasCaptured) {
                        hasCaptured = true
                        navController.navigate(Screen.FoodScan.createRoute(imageUri.toString())) {
                            popUpTo(Screen.Camera.route) { inclusive = true }
                        }
                    }
                }
            )

        } else {
            PermissionDeniedContent(
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
            )
        }
    }
}