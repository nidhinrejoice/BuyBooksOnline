package com.nidhin.customerapp.data.remote.dto

data class UserAddressesResponse(
    val addresses: List<Address>,
    val success: Boolean
)