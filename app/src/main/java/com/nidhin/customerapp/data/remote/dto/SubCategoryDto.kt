package com.nidhin.customerapp.data.remote.dto

import com.nidhin.customerapp.domain.model.SubCategory

data class SubCategoryDto(
    val catId: Int,
    val catName: String,
    val name: String,
    val subCatId: Int
) {
    fun toSubCategory(): SubCategory {
        return SubCategory(
            catId = catId,
            catName = catName,
            name = name,
            subCatId = subCatId
        )
    }
}