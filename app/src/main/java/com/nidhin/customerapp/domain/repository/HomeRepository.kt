package com.nidhin.customerapp.domain.repository

import com.nidhin.customerapp.data.remote.dto.*
import com.nidhin.customerapp.data.remote.dto.Razorpay
import com.nidhin.customerapp.domain.model.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun checkUserLoggedIn(): Boolean

    suspend fun getLatestInventory(): InventoryData
    suspend fun saveItems(items: List<Item>)
    suspend fun saveCategories(items: List<Category>)
    suspend fun saveSubCategories(items: List<SubCategory>)
    suspend fun saveSections(items: List<Section>)
    suspend fun saveRazorpay(razorpay: Razorpay)
    suspend fun saveStore(store: Store)
    suspend fun getUser(): User
    suspend fun getRazorPay() : Razorpay
    suspend fun getBestSellingItems(): List<Item>
    suspend fun getStore(): Store
    suspend fun placeOrder(cartDetails: CartDetails): PlaceOrderResponse
    suspend fun confirmOrder(paymentMetaData: PaymentMetaData): ConfirmPaymentResponse
    suspend fun clearInventory(): Flow<Boolean>
    suspend fun clearCart(): Flow<Boolean>
}