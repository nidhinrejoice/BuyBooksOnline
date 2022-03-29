package com.nidhin.customerapp.data.remote.dto

data class PlaceOrderResponse(
    val order: Order,
    val success: Boolean,
    val message: String
)