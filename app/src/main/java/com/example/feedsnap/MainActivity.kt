package com.example.feedsnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.feedsnap.navigation.AppNavigation
import com.example.feedsnap.ui.theme.FeedSnapTheme
import com.example.feedsnap.view.UploadScreen
import com.example.feedsnap.view.NutritionChart

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            FeedSnapTheme {
//                val navController = rememberNavController()
//
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    NavHost(
//                        navController = navController,
//                        startDestination = "upload",
//                        modifier = Modifier.padding(innerPadding) // ✅ FIX HERE
//                    ) {
//                        composable("upload") {
//                            UploadScreen(
//                                onSelectImageClick = { /* to be implemented */ },
//                                onSubmitClick = {
//                                    navController.navigate("summary")
//                                }
//                            )
//                        }
//
//                        composable("summary") {
//                            NutritionChart(
//                                calories = 1400,
//                                protein = 90,
//                                carbs = 180,
//                                fats = 60
//                            )
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedSnapTheme {
                // Use your AppNavigation composable as the root
                AppNavigation()
            }
        }
    }
}
