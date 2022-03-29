package com.nidhin.customerapp.data.remote

import com.nidhin.customerapp.data.remote.dto.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("app/customer/sendOtp/")
    suspend fun sendOtp(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): ApiResponse

    @POST("app/customer/verifyOtp/")
    suspend fun verifyOtp(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): LoginResponse

    @POST("app/customer/resendOtp/")
    suspend fun resendOtp(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): ApiResponse

    @GET("app/customer/inventory/")
    suspend fun getInventory(
        @Header("client_id") client_id: String, @Query("pincode") pincode: String
    ): InventoryData

    @GET("app/customer/order/{customerId}")
    suspend fun getPastOrders(
        @Header("client_id") client_id: String, @Path("customerId") customerId: Int,
        @Query("pageNo") pageNo: String = "1", @Query("size") size: String = "10"
    ): UserOrdersResponse

    @GET("app/customer/getAddress/{customerId}")
    suspend fun getAddresses(
        @Header("client_id") client_id: String, @Path("customerId") customerId: Int
    ): UserAddressesResponse

    @HTTP(method = "DELETE", path = "app/customer/deleteAddress", hasBody = true)
    suspend fun deleteAddress(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): ApiResponse

    @POST("app/customer/order/")
    suspend fun placeOrder(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): PlaceOrderResponse

    @POST("app/customer/order/confirmpayment")
    suspend fun confirmOrder(
        @Header("client_id") client_id: String,
        @Body body: RequestBody
    ): ConfirmPaymentResponse
}