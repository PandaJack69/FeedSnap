package com.example.feedsnap.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NutritionChart(
    calories: Int = 1400,
    protein: Int = 80,
    carbs: Int = 180,
    fats: Int = 50,
    goalCalories: Int = 2000,
    goalProtein: Int = 100,
    goalCarbs: Int = 250,
    goalFats: Int = 70
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Today's Nutrition", style = MaterialTheme.typography.titleLarge)

        NutritionProgressBar("Calories", calories, goalCalories, Color(0xFFFF9800))
        NutritionProgressBar("Protein (g)", protein, goalProtein, Color(0xFF2196F3))
        NutritionProgressBar("Carbs (g)", carbs, goalCarbs, Color(0xFF4CAF50))
        NutritionProgressBar("Fats (g)", fats, goalFats, Color(0xFF9C27B0))
    }
}

@Composable
fun NutritionProgressBar(label: String, value: Int, goal: Int, color: Color) {
    Column {
        Text("$label: $value / $goal")
        LinearProgressIndicator(
            progress = value.toFloat() / goal.coerceAtLeast(1),
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionChartPreview() {
    NutritionChart()
}
