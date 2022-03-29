package com.nidhin.customerapp.data.remote.dto

data class ConfirmPaymentResponse(
    val order: Order,
    val success: Boolean
)