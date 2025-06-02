package com.example.feedsnap.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feedsnap.FeedSnapApplication
import com.example.feedsnap.domain.model.NutritionInfo
import com.example.feedsnap.features.bmi.BMIScreen
import com.example.feedsnap.features.dashboard.DashboardScreen
import com.example.feedsnap.features.foodscan.CameraCaptureScreen
import com.example.feedsnap.features.foodscan.FoodScanScreen
import com.example.feedsnap.features.foodscan.GallerySelectionScreen
//import com.example.feedsnap.features.foodscan.ImageSelectionScreen
import com.example.feedsnap.features.nutrition.NutritionDetailScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current  // Get current context

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToFoodScan = { navController.navigate(Screen.FoodScan.route) },
                onNavigateToBMI = { navController.navigate(Screen.BMI.route) },
                onOpenCamera = {
                    navController.navigate(Screen.Camera.route)
                },
                onChooseImage = {
                    navController.navigate(Screen.Gallery.route)
                }
//                onSelectFromGallery = { navController.navigate(Screen.Gallery.route) }
            )
        }

        composable(
            route = "foodscan/{imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUriString = backStackEntry.arguments?.getString("imageUri") ?: ""
            val imageUri = Uri.parse(imageUriString)
            FoodScanScreen(imageUri = imageUri, navController = navController)
        }

        // Add new routes for camera and gallery
        composable(Screen.Camera.route) {
            CameraCaptureScreen(navController = navController)
        }

        composable(Screen.Gallery.route) {
            GallerySelectionScreen(navController = navController)
        }

        composable(
            route = "foodScan/{imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUriString = backStackEntry.arguments?.getString("imageUri")
            val imageUri = imageUriString?.let { Uri.parse(it) }

            if (imageUri != null) {
                FoodScanScreen(
                    navController = navController,
                    imageUri = imageUri
                )
            }
        }

        composable(Screen.BMI.route) {
            BMIScreen()
        }

        composable(Screen.NutritionDetail.route) { backStackEntry ->
            val nutritionInfo = backStackEntry.arguments?.getParcelable<NutritionInfo>("nutritionInfo")
            NutritionDetailScreen(nutritionInfo = nutritionInfo)
        }

        composable(
            route = Screen.NutritionDetail.route,
            arguments = listOf(navArgument("nutritionInfo") {
                type = NutritionInfoType()
            })
        ) { backStackEntry ->
            val nutritionInfo = backStackEntry.arguments?.getParcelable<NutritionInfo>("nutritionInfo")
            NutritionDetailScreen(nutritionInfo = nutritionInfo)
        }

    }

}