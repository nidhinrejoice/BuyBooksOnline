package com.nidhin.customerapp.data.remote.dto

data class InventoryData(
    val categories: List<CategoryDto>,
    val items: List<ItemDto>,
    val razorpay: Razorpay,
    val sections: List<SectionDto>,
    val store: Store,
    val subCategories: List<SubCategoryDto>,
    val success: Boolean,
    val message: String
)