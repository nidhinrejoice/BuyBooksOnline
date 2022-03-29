package com.nidhin.customerapp.domain.usecases.login

import com.nidhin.customerapp.data.remote.dto.LoginResponse
import com.nidhin.customerapp.data.remote.dto.User
import com.nidhin.customerapp.domain.repository.UserLoginRepository

class FakeLoginRepository :UserLoginRepository {
    override suspend fun sendOtp(mobile: String): String {
        return "otp sent"
    }

    override suspend fun resendOtp(mobile: String): String {
        return "otp sent"
    }

    override suspend fun verifyOtp(otp: String, mobile: String): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(mobile: User) {
        TODO("Not yet implemented")
    }
}