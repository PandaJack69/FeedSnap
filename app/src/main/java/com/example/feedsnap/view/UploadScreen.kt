package com.example.feedsnap.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            text = "Upload Your Meal",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Image Preview Card
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "Selected Meal Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder_image),
//                    contentDescription = "Placeholder Image",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    contentScale = ContentScale.Fit
//                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onSelectImageClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Choose Image", fontSize = 16.sp, color = Color.White)
            }

            Button(
                onClick = onSubmitClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Analyze", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UploadScreenPreview() {
    UploadScreen()
}
