package com.nidhin.customerapp.domain.model

data class ListingItem(
    val itemCode: String,
    val itemId: Int,
    val itemName: String,
    val displayName: String?,
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
    var quantity : Int
){

}
