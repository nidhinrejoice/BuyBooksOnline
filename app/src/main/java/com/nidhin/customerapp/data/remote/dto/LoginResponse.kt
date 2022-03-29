package com.nidhin.customerapp.data.remote.dto

data class LoginResponse(
    val message: String,
    val success: Boolean,
    val user: User
)