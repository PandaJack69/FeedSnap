package com.example.feedsnap.features.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feedsnap.domain.model.NutritionInfo
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Button

@Composable
fun DashboardScreen(
    onNavigateToFoodScan: () -> Unit,
    onNavigateToBMI: () -> Unit,
    nutritionHistory: List<NutritionInfo> = emptyList(),
    onChooseImage: () -> Unit,
    onOpenCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
//    val nutritionHistory by viewModel.nutritionHistory.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Added header with navigation options
        Button(
            onClick = onOpenCamera,
            modifier = Modifier.padding(16.dp)
        ) {
//            Text("Take Photo")
            Text("Ambil Foto")

        }

        Button(
            onClick = onChooseImage,
            modifier = Modifier.padding(16.dp)
        ) {
//            Text("Choose from Gallery")
            Text("Gallery")
        }

        DashboardHeader(
            onScanClick = onNavigateToFoodScan,
            onBMIClick = onNavigateToBMI
        )

        Text(
            text = "Nutrition History",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (nutritionHistory.isEmpty()) {
            EmptyHistoryMessage()
        } else {
            NutritionHistoryList(nutritionHistory) { item ->
                // Handle item click - could navigate to detail view
                onNavigateToFoodScan() // Example - replace with your navigation
            }
        }
    }
}

@Composable
private fun DashboardHeader(
    onScanClick: () -> Unit,
    onBMIClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "FeedSnap Dashboard",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Track your nutrition intake",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun NutritionHistoryList(
    items: List<NutritionInfo>,
    onItemClick: (NutritionInfo) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            NutritionHistoryItem(
                item = item,
                onClick = { onItemClick(item) }  // Now properly ordered
            )
        }
    }
}

@Composable
fun NutritionHistoryItem(
    item: NutritionInfo,
    onClick: () -> Unit,  // onClick comes before modifier
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.foodName, style = MaterialTheme.typography.titleLarge)
            Text(text = "${item.calories} kcal", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = buildString {
                    append("Carbs: ${item.nutrients["carbs"] ?: 0}g | ")
                    append("Protein: ${item.nutrients["protein"] ?: 0}g | ")
                    append("Fat: ${item.nutrients["fat"] ?: 0}g")
                },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun EmptyHistoryMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No nutrition history yet",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Text(
            text = "Scan your first meal to get started!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }
}