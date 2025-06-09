package com.example.feedsnap_frontend.models

object BMIModel {
    fun calculateBMI(weight: Double, height: Double): Double {
        return weight / ((height / 100) * (height / 100))
    }

    fun getCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }
    }

    fun calculateDailyCalories(
        weight: Double,
        height: Double,
        age: Int,
        isMale: Boolean
    ): Int {
        val bmr = if (isMale) {
            88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
        }
        return (bmr * 1.2).toInt() // Sedentary activity level
    }
}