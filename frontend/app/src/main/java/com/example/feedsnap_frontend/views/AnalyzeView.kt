package com.example.feedsnap_frontend.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.feedsnap_frontend.R
import com.example.feedsnap_frontend.controllers.AnalyzeController

class AnalyzeView : AppCompatActivity() {
    lateinit var btnTakePicture: Button
    lateinit var btnSelectGallery: Button
    lateinit var imgFood: ImageView
    private lateinit var controller: AnalyzeController

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.analyze_view)

        btnTakePicture = findViewById(R.id.btnTakePicture)
        btnSelectGallery = findViewById(R.id.btnSelectGallery)
        imgFood = findViewById(R.id.imgFood)

        controller = AnalyzeController(this, this)

        btnTakePicture.setOnClickListener {
            checkCameraPermissionAndCapture()
        }

        btnSelectGallery.setOnClickListener {
            controller.launchGallery()
        }
    }

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
