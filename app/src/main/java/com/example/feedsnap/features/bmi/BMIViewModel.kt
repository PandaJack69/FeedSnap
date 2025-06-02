package com.example.feedsnap.features.bmi

import androidx.lifecycle.ViewModel

class BMIViewModel : ViewModel() {
    fun calculateBMI(heightCm: Float, weightKg: Float): Pair<Float, String> {
        val heightM = heightCm / 100
        val bmi = weightKg / (heightM * heightM)

        val category = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }

        return bmi to category
    }
}