package com.nidhin.customerapp.data.repository

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.data.remote.ApiService
import com.nidhin.customerapp.data.remote.dto.*
import com.nidhin.customerapp.data.remote.dto.Address
import com.nidhin.customerapp.domain.repository.UserRepository
import com.nidhin.customerapp.persistance.AppDatabase
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val gson: Gson
) : UserRepository {


    override suspend fun getUser(): User {
        return gson.fromJson(sharedPrefsHelper.get("user", ""), User::class.java)
    }

    override suspend fun getUserOrders(): List<Order> {
        return apiService.getPastOrders(Constants.API_KEY, getUser().customerId, "1", "10")
            .orders
    }

    override suspend fun getAddresses(): List<Address> {
        return apiService.getAddresses(Constants.API_KEY, getUser().customerId)
            .addresses
    }

    override suspend fun deleteAddress(addressId:String): ApiResponse{
        var json = JSONObject()
        json.put("customerId",getUser().customerId)
        json.put("addressId",addressId)
        val body: RequestBody = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.deleteAddress(Constants.API_KEY, body)
    }

    override suspend fun logout(): Boolean {
        sharedPrefsHelper.removeKey("user")
        return true
    }
}