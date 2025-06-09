package com.example.feedsnap_frontend.controllers

import android.content.Context
import com.example.feedsnap_frontend.views.DashBoardView
import com.example.feedsnap_frontend.views.AnalyzeView
import com.example.feedsnap_frontend.views.BMIView
import android.content.Intent

class DashboardController(
    private val view: DashBoardView,
    private val context: Context
) {
    fun setupListeners() {
        view.btnAnalyzeFood.setOnClickListener {
            val intent = Intent(context, AnalyzeView::class.java)
            context.startActivity(intent)
        }

        view.btnBMICalculator.setOnClickListener {
            val intent = Intent(context, BMIView::class.java)
            context.startActivity(intent)
        }
    }
}