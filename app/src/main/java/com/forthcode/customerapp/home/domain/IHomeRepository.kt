package com.forthcode.customerapp.home.domain

import com.forthcode.customerapp.models.Category
import com.forthcode.customerapp.models.Item
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {

    suspend fun getInventoryCategories(): Flow<List<Category>>
    suspend fun getPincode(): Flow<String?>
}