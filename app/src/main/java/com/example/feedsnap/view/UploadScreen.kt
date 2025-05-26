package com.example.feedsnap.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.feedsnap.R

@Composable
fun UploadScreen(
    imageUri: Uri? = null,
    onSelectImageClick: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Upload Your Meal",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Show selected image or placeholder
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Selected Meal Image",
                modifier = Modifier
                    .size(240.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        } else {
//            Image(
//                painter = painterResource(id = R.drawable.placeholder_image),
//                contentDescription = "Placeholder Image",
//                modifier = Modifier
//                    .size(240.dp)
//                    .padding(8.dp),
//                contentScale = ContentScale.Crop
//            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSelectImageClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text("Choose Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmitClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Analyze", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadScreenPreview() {
    UploadScreen()
}
