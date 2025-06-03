package com.example.feedsnap.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Base64ImageRequest(
    val image: String // This should be your base64 image string
)
