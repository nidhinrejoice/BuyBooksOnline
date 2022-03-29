package com.nidhin.customerapp.data.remote.dto

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.domain.model.Item

data class ItemDto(
    val addon: Boolean,
    val available: Boolean,
    val barcode: String,
    val baseUnit: Int,
    val beverage: Boolean,
    val catId: Int,
    val catName: String,
    val cess: Int,
    val cgst: Int,
    val colorCode: Any,
    val comboItem: Boolean,
    val currentStock: Int,
    val description: String,
    val displayName: String,
    val gstType: String,
    val hsn: String,
    val igst: Int,
    val image: String?,
    val intlPrices: List<Any>,
    val itemCode: String,
    val itemId: Int,
    val itemName: String,
    val itemPrice: Float,
    val itemType: String,
    val meal: Boolean,
    val newItem: Boolean,
    val referenceIds: List<Any>,
    val sales: Int,
    val section: String,
    val serviceCharge: Int,
    val servings: List<Any>,
    val sgst: Int,
    val shortDescription: String,
    val specialPrice: Float,
    val subCatId: Int,
    val subCatName: String,
    val unit: String,
    val utgst: Int,
    val variantItem: Boolean,
    val variants: List<Any>,
    val vegetarian: Boolean
)

fun ItemDto.toItem(): Item {
    return Item(
        itemId = itemId,
        itemCode = itemCode,
        category = catName,
        catId = catId,
        subCategory = subCatName,
        subCategoryId = subCatId,
        description = description,
        shortDescription = shortDescription,
        displayName = displayName,
        image = image?.let {
            if (!it.isNullOrEmpty()) {
                Constants.API_END_POINT + it
            } else {
                ""
            }
        } ?: "",
        itemName = itemName,
        itemPrice = itemPrice,
        specialPrice = specialPrice,
        newItem = newItem,
        section = section,
        unit = unit,
        sales = sales,
        available = available,
        currentStock = currentStock
    )
}