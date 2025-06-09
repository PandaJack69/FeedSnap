package com.example.feedsnap_frontend.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.feedsnap_frontend.R
import com.example.feedsnap_frontend.controllers.AnalyzeController
import com.example.feedsnap_frontend.services.RetrofitClient

class AnalyzeView : AppCompatActivity() {
    lateinit var btnTakePicture: Button
    lateinit var btnSelectGallery: Button
    lateinit var btnSendForAnalysis: Button // Add this
    lateinit var imgFood: ImageView
    private lateinit var controller: AnalyzeController

    private var currentImageUri: Uri? = null // Track the current image URI

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.analyze_view)

        btnTakePicture = findViewById(R.id.btnTakePicture)
        btnSelectGallery = findViewById(R.id.btnSelectGallery)
        btnSendForAnalysis = findViewById(R.id.btnSendForAnalysis) // Initialize
        imgFood = findViewById(R.id.imgFood)

        controller = AnalyzeController(this, this)

        btnTakePicture.setOnClickListener {
            checkCameraPermissionAndCapture()
        }

        btnSelectGallery.setOnClickListener {
            controller.launchGallery()
        }

        // Add click listener for the new button
        btnSendForAnalysis.setOnClickListener {
            currentImageUri?.let { uri ->
                controller.uploadImageToServer(uri, this)
            }
        }
    }

    // Add this function to enable/disable the button
    fun setImageUploadEnabled(enabled: Boolean) {
        btnSendForAnalysis.isEnabled = enabled
    }

    // Add this function to set the current image URI
    fun setCurrentImageUri(uri: Uri) {
        currentImageUri = uri
        setImageUploadEnabled(true)
    }

//    fun showPredictionResult(prediction: String) {
//        // Create a TextView to show the result
//        val resultView = TextView(this).apply {
//            text = "Prediction: $prediction"
//            textSize = 18f
//            gravity = Gravity.CENTER
//            setTextColor(ContextCompat.getColor(this@AnalyzeView, R.color.black))
//        }
//
//        // Add below the image
//        val layout = findViewById<LinearLayout>(R.id.analyze_layout)
//        layout.addView(resultView, layout.indexOfChild(btnSendForAnalysis) + 1)
//    }
    private var resultTextView: TextView? = null
    private var nutritionView: TextView? = null

    fun showPredictionResult(prediction: String) {
        val layout = findViewById<LinearLayout>(R.id.analyze_layout)

        // Remove existing views if they exist
        resultTextView?.let { layout.removeView(it) }
        nutritionView?.let { layout.removeView(it) }

        // Create and add the result text view
        resultTextView = TextView(this).apply {
            text = "Prediction: $prediction"
            textSize = 18f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setTypeface(null, Typeface.BOLD)
        }
        val sendButton = findViewById<Button>(R.id.btnSendForAnalysis)
        layout.addView(resultTextView, layout.indexOfChild(sendButton) + 1)

        // Call controller to fetch nutrition info
        controller.fetchNutrition(
            prediction,
            onSuccess = { nutrients ->
                val nutritionText = nutrients.joinToString("\n") {
                    "${it.name}: ${it.value} ${it.unit} ${if (it.percent != 0.0) "(${it.percent}%)" else ""}"
                }

                nutritionView = TextView(this).apply {
                    text = "\nNutrition (per 100g):\n$nutritionText"
                    textSize = 16f
                    setTextColor(Color.WHITE)
                    setPadding(0, 16, 0, 0)
                }
                layout.addView(nutritionView, layout.indexOfChild(resultTextView) + 1)
            },
            onFailure = { error ->
                Log.e("NutritionFetch", "Failed to fetch nutrition info: ${error.message}")
                Toast.makeText(this, "Nutrition info not available", Toast.LENGTH_SHORT).show()
            }
        )
    }

//    fun showPredictionResult(prediction: String) {
//        val layout = findViewById<LinearLayout>(R.id.analyze_layout)
//
//        // Remove old prediction view if it exists
//        resultTextView?.let { layout.removeView(it) }
//
//        // Create new prediction view
//        resultTextView = TextView(this).apply {
//            text = "Prediction: $prediction"
//            textSize = 18f
//            gravity = Gravity.CENTER
//            setTextColor(ContextCompat.getColor(this@AnalyzeView, android.R.color.white))
//            setTypeface(null, Typeface.BOLD)
//            setPadding(0, 16, 0, 0)
//        }
//
//        // Insert below the send button
//        val sendButton = findViewById<Button>(R.id.btnSendForAnalysis)
//        layout.addView(resultTextView, layout.indexOfChild(sendButton) + 1)
//    }

    private fun checkCameraPermissionAndCapture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            controller.launchCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                controller.launchCamera()
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        controller.handleActivityResult(requestCode, resultCode, data)
    }
}
