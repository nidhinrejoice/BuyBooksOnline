package com.forthcode.customerapp.signup.domain

import com.forthcode.customerapp.login.data.ApiResponse
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ISignupRepository {
    suspend fun signUp(request: JSONObject): Flow<ApiResponse>
    suspend fun getMobile(): Flow<String?>
    suspend fun changeNumber(): Flow<Boolean>


}