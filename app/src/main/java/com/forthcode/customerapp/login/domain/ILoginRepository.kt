package com.forthcode.customerapp.login.domain

import com.forthcode.customerapp.login.data.ApiResponse
import com.forthcode.customerapp.login.data.UserFlow
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {

    suspend fun sendOtp(mobile: String): Flow<ApiResponse>

    suspend fun verifyOtp(otp: String, mobile: String): Flow<UserFlow>
    suspend fun checkUser(): Flow<UserFlow>
    suspend fun changeNumber(): Flow<Boolean>
}