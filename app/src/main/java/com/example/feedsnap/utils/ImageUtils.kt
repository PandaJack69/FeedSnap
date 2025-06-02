package com.example.feedsnap.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageUtils {
    fun imageProxyToBase64(image: ImageProxy): String {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun uriToBase64(context: Context, uri: Uri): String {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(4 * 1024) // 4KB buffer
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        }
        return ""
    }

    fun bitmapToBase64(bitmap: Bitmap, quality: Int = 90): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val bytes = outputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun uriToBase64(context: Context, uri: Uri, quality: Int = 90): String {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream)
            return bitmapToBase64(bitmap, quality)
        }
        return ""
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }

}