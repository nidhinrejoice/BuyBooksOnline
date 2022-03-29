package com.forthcode.customerapp.api

import com.forthcode.customerapp.Constants
import com.forthcode.customerapp.login.data.ApiResponse
import com.forthcode.customerapp.login.data.VerifyOtpResponse
import com.forthcode.customerapp.models.InventoryListing
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @Headers("client_id:" + Constants.ID)
    @POST("v2/customer/sendOtp")
    suspend fun sendOtp(@Body loginRequest: RequestBody): ApiResponse

    @Headers("client_id:" + Constants.ID)
    @POST("v2/customer/verifyOtp")
    suspend fun verifyOtp(@Body requestBody: RequestBody): VerifyOtpResponse

    @Headers("client_id:" + Constants.ID)
    @POST("customer/signup")
    suspend fun signup(@Body requestBody: RequestBody): ApiResponse

    @Headers("client_id:" + Constants.ID)
    @GET("customer/inventory")
    suspend fun refreshMenu(@Query("pincode") pincode: String?): InventoryListing

}