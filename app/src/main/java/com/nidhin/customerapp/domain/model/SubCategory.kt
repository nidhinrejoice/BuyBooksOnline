package com.nidhin.customerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subcategories")
data class SubCategory(
    @PrimaryKey
    val subCatId: Int,
    val catId: Int,
    val catName: String,
    val name: String
)
