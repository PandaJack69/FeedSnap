package com.example.feedsnap.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_history")
data class NutritionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val foodName: String,
    val calories: Float,
    val nutrientsJson: String, // Stored as JSON string
    val date: Long = System.currentTimeMillis()
)