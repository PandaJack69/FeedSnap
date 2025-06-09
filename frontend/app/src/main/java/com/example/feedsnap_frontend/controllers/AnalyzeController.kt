package com.example.feedsnap_frontend.controllers

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.feedsnap_frontend.views.AnalyzeView

class AnalyzeController(
    private val view: AnalyzeView,
    private val activity: AppCompatActivity
) {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
    }

    fun launchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun launchGallery() {
        val pickPhotoIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
    }

    private fun loadImageFromUri(uri: Uri) {
        Glide.with(activity)
            .load(uri)
            .centerCrop()
            .into(view.imgFood)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val imageBitmap = data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    view.imgFood.setImageBitmap(it)
                }
            }
            REQUEST_IMAGE_PICK -> {
                val selectedImage = data?.data
                selectedImage?.let {
                    loadImageFromUri(it)
                }
            }
        }
    }
}
