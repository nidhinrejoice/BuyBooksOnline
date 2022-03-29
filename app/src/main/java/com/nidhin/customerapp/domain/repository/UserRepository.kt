package com.nidhin.customerapp.domain.repository

import com.nidhin.customerapp.data.remote.dto.*
import com.nidhin.customerapp.data.remote.dto.Address

interface UserRepository {

    suspend fun getUser(): User
    suspend fun getUserOrders(): List<Order>
    suspend fun getAddresses(): List<Address>
    suspend fun deleteAddress(addressId: String): ApiResponse
    suspend fun logout():Boolean
}