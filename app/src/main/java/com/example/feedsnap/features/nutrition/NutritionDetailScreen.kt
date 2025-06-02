package com.example.feedsnap.features.nutrition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedsnap.domain.model.NutritionInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun NutritionDetailScreen(
    nutritionInfo: NutritionInfo?,
    viewModel: NutritionViewModel = koinViewModel()
) {
//    val state by viewModel.state.collectAsState()
//    val state by viewModel.state.collectAsStateWithLifecycle()
    val state by viewModel.state // ✅


    if (nutritionInfo == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No nutrition data available")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = nutritionInfo.foodName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Calories: ${nutritionInfo.calories} kcal",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Nutritional Values:", style = MaterialTheme.typography.titleMedium)

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            nutritionInfo.nutrients.forEach { (name, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(name.capitalize())
                    Text("$value g")
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveToMealPlan(nutritionInfo) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to Meal Plan")
        }
    }
}