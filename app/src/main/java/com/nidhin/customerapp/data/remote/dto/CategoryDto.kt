package com.nidhin.customerapp.data.remote.dto

import com.nidhin.customerapp.domain.model.Category

data class CategoryDto(
    val catId: Int,
    val catName: String,
    val description: String,
    val image: String
) {
    fun toCategory():Category {
        return Category(
            image = image,
            description = description,
            catId = catId,
            catName = catName
        )
    }
}