package com.example.feedsnap_frontend.views

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.feedsnap_frontend.R
import com.example.feedsnap_frontend.controllers.DashboardController

class DashBoardView : AppCompatActivity() {
    lateinit var btnAnalyzeFood: Button
    lateinit var btnBMICalculator: Button
    private lateinit var controller: DashboardController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)

        btnAnalyzeFood = findViewById(R.id.btnAnalyzeFood)
        btnBMICalculator = findViewById(R.id.btnBMICalculator)

        controller = DashboardController(this, this)
        controller.setupListeners()
    }
}