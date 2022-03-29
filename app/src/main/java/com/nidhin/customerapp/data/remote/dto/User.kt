package com.nidhin.customerapp.data.remote.dto

data class User(
    var addresses: List<Address>,
    val customerId: Int,
    val customerName: String,
    val dateAdded: String,
    val deleted: Boolean,
    var email: String,
    var firstName: String,
    var lastName: String,
    val lastOrderId: Int,
    val loyaltyPoints: Int,
    val mobile: String,
    val nextAddressId: Int,
    val protocol: String
)