package com.nidhin.customerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    val itemCode: String,
    val itemId: Int,
    val itemName: String,
    val displayName: String?,
    val shortDescription: String?,
    val description: String?,
    val section: String,
    val category: String,
    val catId: Int,
    val subCategory: String,
    val subCategoryId: Int,
    val image: String?,
    val itemPrice: Float,
    val specialPrice: Float,
    val newItem: Boolean?,
    val available: Boolean?,
    val sales: Int,
    val currentStock: Int,
    val unit: String?
)
