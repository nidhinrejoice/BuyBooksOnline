package com.nidhin.customerapp.domain.model

data class CartDetails(
    var totalAmount: Float = 0f,
    var totalItems: Long = 0,
    var totalDiscount: Float = 0f,
    var totalPromoDiscount: Float = 0f,
    var payableAmount: Float = 0f,
    var totalBill: Float = 0f,
    var deliveryCharges: Float = 0f,
    var packingCharges: Float = 0f,
    var promoCodeApplied: String = "",
    var cartItems: List<ListingItem> = listOf()
)
