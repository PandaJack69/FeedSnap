package com.example.feedsnap.features.foodscan

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.feedsnap.navigation.Screen
import com.example.feedsnap.utils.ImageUtils
import org.koin.androidx.compose.koinViewModel
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun GallerySelectionScreen(
    navController: NavController,
    viewModel: FoodScanViewModel = koinViewModel()
) {
    val context = LocalContext.current
//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            val base64Image = ImageUtils.uriToBase64(context, it)
//            viewModel.selectedImage = base64Image
//            viewModel.analyzeBase64Image(base64Image)
//            navController.popBackStack() // Return to previous screen
//            navController.navigate(Screen.FoodScan.route) // Navigate to food scan
//        } ?: navController.popBackStack() // User canceled
//    }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
//            val base64Image = ImageUtils.uriToBase64(context, it)
//            viewModel.analyzeBase64Image(base64Image)
//            navController.navigate(Screen.FoodScan.route) {
//                popUpTo(Screen.Gallery.route) { inclusive = true }
//            }
            navController.navigate(Screen.FoodScan.createRoute(it.toString())) {
                popUpTo(Screen.Gallery.route) { inclusive = true }
            }

        }
    }

    var hasLaunched by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLaunched) {
            hasLaunched = true
            imagePicker.launch("image/*")
        }
    }
//    LaunchedEffect(Unit) {
//        imagePicker.launch("image/*")
//    }

    // Show loading indicator while waiting for image selection
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}