package com.nidhin.customerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val catId: Int,
    val catName: String,
    val description: String,
    val image: String
)
