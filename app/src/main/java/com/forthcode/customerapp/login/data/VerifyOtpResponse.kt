package com.forthcode.customerapp.login.data

import com.forthcode.customerapp.models.UserEntity

data class VerifyOtpResponse (
    val success:Boolean,
    val message:String,
    val user: UserEntity

)