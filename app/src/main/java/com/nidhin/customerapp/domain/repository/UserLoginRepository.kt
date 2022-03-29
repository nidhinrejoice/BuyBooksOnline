package com.nidhin.customerapp.domain.repository

import com.nidhin.customerapp.data.remote.dto.LoginResponse
import com.nidhin.customerapp.data.remote.dto.User

interface UserLoginRepository {

    suspend fun sendOtp(mobile: String): String
    suspend fun resendOtp(mobile: String): String
    suspend fun verifyOtp(otp: String, mobile: String): LoginResponse
    suspend fun loginUser(mobile: User)
}