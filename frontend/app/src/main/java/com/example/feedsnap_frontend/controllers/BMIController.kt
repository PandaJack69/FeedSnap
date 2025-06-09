package com.example.feedsnap_frontend.controllers

import android.widget.Toast
import com.example.feedsnap_frontend.R
import com.example.feedsnap_frontend.models.BMIModel
import com.example.feedsnap_frontend.views.BMIView
import android.content.Context

class BMIController(private val view: BMIView) {
    fun setupListeners() {
        view.btnCalculate.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        try {
            val weight = view.etWeight.text.toString().toDouble()
            val height = view.etHeight.text.toString().toDouble()
            val age = view.etAge.text.toString().toInt()
            val isMale = view.rgGender.checkedRadioButtonId == R.id.rbMale

            val bmi = BMIModel.calculateBMI(weight, height)
            val category = BMIModel.getCategory(bmi)
            val calories = BMIModel.calculateDailyCalories(weight, height, age, isMale)

            view.tvBMIResult.text = "BMI: ${String.format("%.1f", bmi)}"
            view.tvCategory.text = "Category: $category"
            view.tvCalorie.text = "Daily Calories: $calories kcal"
        } catch (e: Exception) {
//            Toast.makeText(view.context, "Please enter valid values", Toast.LENGTH_SHORT).show()
            Toast.makeText(view, "Please enter valid values", Toast.LENGTH_SHORT).show()

        }
    }
}