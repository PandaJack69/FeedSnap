package com.example.feedsnap_frontend.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.feedsnap_frontend.R
import com.example.feedsnap_frontend.controllers.BMIController

class BMIView : AppCompatActivity() {
    lateinit var etWeight: EditText
    lateinit var etHeight: EditText
    lateinit var etAge: EditText
    lateinit var rgGender: RadioGroup
    lateinit var btnCalculate: Button
    lateinit var tvBMIResult: TextView
    lateinit var tvCategory: TextView
    lateinit var tvCalorie: TextView
    private lateinit var controller: BMIController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_view)

        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        etAge = findViewById(R.id.etAge)
        rgGender = findViewById(R.id.rgGender)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvBMIResult = findViewById(R.id.tvBMIResult)
        tvCategory = findViewById(R.id.tvCategory)
        tvCalorie = findViewById(R.id.tvCalorie)

        controller = BMIController(this)
        controller.setupListeners()
    }
}