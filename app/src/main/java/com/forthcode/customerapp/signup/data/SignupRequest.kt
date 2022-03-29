package com.forthcode.customerapp.signup.data

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val email: String
)