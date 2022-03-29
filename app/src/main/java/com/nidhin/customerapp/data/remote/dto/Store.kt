package com.nidhin.customerapp.data.remote.dto

data class Store(
    val deliveryCharge: Float,
    val deliveryChargePerItem: Float,
    val priceList: String,
    val priceListModifiedAt: String,
    val rating: Float,
    val storeAddress: String,
    val storeId: Int,
    val storeName: String
)