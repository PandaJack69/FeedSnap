package com.example.feedsnap.features.foodscan.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.feedsnap.domain.model.NutritionInfo
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NutritionResultPopup(
    nutritionInfo: NutritionInfo,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(
                text = "Nutrition Analysis",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = nutritionInfo.foodName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Calories: ${"%.1f".format(nutritionInfo.calories)} kcal",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                nutritionInfo.nutrients.forEach { (name, value) ->
                    Text(
                        text = "${name.capitalize()}: ${"%.1f".format(value)}g",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save to History")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview
@Composable
fun NutritionResultPopupPreview() {
    val sampleNutrition = NutritionInfo(
        foodName = "Grilled Chicken Salad",
        calories = 350f,
        nutrients = mapOf(
            "protein" to 30f,
            "carbs" to 12f,
            "fat" to 18f
        ),
        servingSize = "1 bowl",
        confidence = 0.85f
    )

    NutritionResultPopup(
        nutritionInfo = sampleNutrition,
        onDismiss = {},
        onSave = {}
    )
}