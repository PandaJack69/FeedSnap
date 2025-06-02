package com.example.feedsnap.features.foodscan.components

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@androidx.annotation.OptIn(ExperimentalCamera2Interop::class)
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    val cameraSelector = remember {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )

//        Button(
//            onClick = {
//                imageCapture.takePicture(
//                    ContextCompat.getMainExecutor(context),
//                    object : ImageCapture.OnImageCapturedCallback() {
//                        override fun onCaptureSuccess(image: ImageProxy) {
//                            super.onCaptureSuccess(image)
//                            try {
//                                val bitmap = imageProxyToBitmap(image)
//                                val file = File(context.cacheDir, "captured_${System.currentTimeMillis()}.jpg")
//                                val outputStream = FileOutputStream(file)
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                                outputStream.flush()
//                                outputStream.close()
//
//                                val uri = FileProvider.getUriForFile(
//                                    context,
//                                    "${context.packageName}.provider",
//                                    file
//                                )
//                                onImageCaptured(uri)
//                            } catch (e: Exception) {
//                                Log.e("CameraPreview", "Bitmap save failed", e)
//                            } finally {
//                                image.close()
//                            }
//                        }
//
//                        override fun onError(exception: ImageCaptureException) {
//                            super.onError(exception)
//                            Log.e("CameraPreview", "Capture failed", exception)
//                        }
//                    }
//                )
//            },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 32.dp)
//        ) {
//            Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "Capture")
//        }

        Button(
            onClick = {
                // Create temp file in cache dir
                val photoFile = File.createTempFile("img_", ".jpg", context.cacheDir)

                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            // Get the saved Uri
                            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                            // Call your callback with the Uri (instead of ImageProxy)
                            onImageCaptured(savedUri)
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e("CameraPreview", "Capture failed", exception)
                        }
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "Capture")
        }

    }
}

// Helper to convert ImageProxy to Bitmap
@OptIn(ExperimentalGetImage::class)
fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
    val image: Image = imageProxy.image ?: throw IllegalArgumentException("Image is null")
    val yBuffer = image.planes[0].buffer
    val uBuffer = image.planes[1].buffer
    val vBuffer = image.planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)
    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, imageProxy.width, imageProxy.height), 100, out)
    val jpegBytes = out.toByteArray()

    return android.graphics.BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
}
