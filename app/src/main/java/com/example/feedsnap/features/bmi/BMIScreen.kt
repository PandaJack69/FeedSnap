package com.example.feedsnap.features.bmi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun BMIScreen(viewModel: BMIViewModel = koinViewModel()) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Pair<Float, String>?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val h = height.toFloatOrNull() ?: 0f
                val w = weight.toFloatOrNull() ?: 0f
                if (h > 0 && w > 0) {
                    result = viewModel.calculateBMI(h, w)
                }
            }
        ) {
            Text("Calculate BMI")
        }

        result?.let { (bmiValue, category) ->
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Your BMI: ${"%.1f".format(bmiValue)}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Category: $category",
                style = MaterialTheme.typography.bodyLarge,
                color = when (category) {
                    "Underweight" -> Color.Blue
                    "Normal" -> Color.Green
                    "Overweight" -> Color.Yellow
                    "Obese" -> Color.Red
                    else -> Color.Gray
                }
            )
        }
    }
}