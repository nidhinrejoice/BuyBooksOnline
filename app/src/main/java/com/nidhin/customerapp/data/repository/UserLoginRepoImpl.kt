package com.nidhin.customerapp.data.repository

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.data.remote.ApiService
import com.nidhin.customerapp.data.remote.dto.LoginResponse
import com.nidhin.customerapp.data.remote.dto.User
import com.nidhin.customerapp.domain.repository.UserLoginRepository
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserLoginRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val gson: Gson
) : UserLoginRepository {

    override suspend fun sendOtp(mobile: String): String {
        val jsonReq = JSONObject()
        jsonReq.put("userId", mobile)
        val body: RequestBody = jsonReq.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.sendOtp(Constants.API_KEY, body).message
    }

    override suspend fun verifyOtp(otp: String,mobile: String): LoginResponse {
        val jsonReq = JSONObject()
        jsonReq.put("userId", mobile)
        jsonReq.put("otp", otp)
        val body: RequestBody = jsonReq.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.verifyOtp(Constants.API_KEY, body)
    }

    override suspend fun resendOtp(mobile: String): String {
        val jsonReq = JSONObject()
        jsonReq.put("userId", mobile)
        val body: RequestBody = jsonReq.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.resendOtp(Constants.API_KEY, body).message
    }

    override suspend fun loginUser(user: User) {
         sharedPrefsHelper.put("user",gson.toJson(user,User::class.java))
    }
}