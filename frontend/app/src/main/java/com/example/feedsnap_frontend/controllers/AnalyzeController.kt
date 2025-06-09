package com.example.feedsnap_frontend.controllers

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.WindowInsetsAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageProcessor
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.feedsnap_frontend.services.Nutrient
import com.example.feedsnap_frontend.services.NutritionResponse
import com.example.feedsnap_frontend.services.PredictionResponse
import com.example.feedsnap_frontend.services.RetrofitClient
import com.example.feedsnap_frontend.views.AnalyzeView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream

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
                imageBitmap?.let { bitmap ->
                    // Save bitmap to file and get URI
                    val uri = saveBitmapToCache(bitmap)
                    view.setCurrentImageUri(uri)
                    view.imgFood.setImageBitmap(bitmap)
                }
            }
            REQUEST_IMAGE_PICK -> {
                val selectedImage = data?.data
                selectedImage?.let { uri ->
                    view.setCurrentImageUri(uri)
                    loadImageFromUri(uri)
                }
            }
        }
    }

    // Save bitmap to cache and return URI
    private fun saveBitmapToCache(bitmap: Bitmap): Uri {
        val file = File(activity.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.provider",
            file
        )
    }

    //For backend handles
    fun uploadImageToServer(imageUri: Uri, activity: Activity) {
        val context = activity.applicationContext
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)

        // Create temp file
        val file = File.createTempFile("upload", ".jpg", context.cacheDir).apply {
            deleteOnExit()
        }

        inputStream?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val call = RetrofitClient.instance.uploadImage(body)
        call.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction ?: "Unknown"
                    // Show prediction in UI
                    view.showPredictionResult(prediction)
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Toast.makeText(context, "Upload failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun fetchNutrition(prediction: String, onSuccess: (List<Nutrient>) -> Unit, onFailure: (Throwable) -> Unit) {
        val service = RetrofitClient.nutritionService
        service.getNutritionInfo(prediction).enqueue(object : Callback<NutritionResponse> {
            override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                if (response.isSuccessful && response.body()?.status == "success") {
                    val nutrients = response.body()?.data ?: emptyList()
                    onSuccess(nutrients)
                } else {
                    onFailure(Exception("Unexpected response code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }


}
