package com.nidhin.customerapp.data.remote.dto

data class UserOrdersResponse(
    val orders: List<Order>,
    val success: Boolean
)