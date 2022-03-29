package com.nidhin.customerapp.data.repository

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.data.remote.ApiService
import com.nidhin.customerapp.data.remote.dto.*
import com.nidhin.customerapp.data.remote.dto.Address
import com.nidhin.customerapp.domain.model.*
import com.nidhin.customerapp.domain.repository.HomeRepository
import com.nidhin.customerapp.persistance.AppDatabase
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import com.nidhin.customerapp.utils.getFormattedDateInIso
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val gson: Gson
) : HomeRepository {

    override suspend fun getLatestInventory(): InventoryData {
        var pincode = ""
        if (getUser().addresses.isNotEmpty()) {
            pincode = getUser().addresses[0].pincode.toString()
        }
        return apiService.getInventory(Constants.API_KEY, pincode)
            .apply {
                sharedPrefsHelper.put(
                    "inventoryUpdatedAt",
                    Calendar.getInstance().timeInMillis
                )
            }
    }

    override suspend fun saveItems(items: List<Item>) {
        database.inventoryDao().saveItems(items)
    }

    override suspend fun saveCategories(items: List<Category>) {
        database.inventoryDao().saveCategories(items)
    }

    override suspend fun saveSubCategories(items: List<SubCategory>) {
        database.inventoryDao().saveSubCategories(items)
    }

    override suspend fun saveSections(items: List<Section>) {
        database.inventoryDao().saveSections(items)
    }

    override suspend fun saveRazorpay(razorpay: Razorpay) {
        sharedPrefsHelper.put("razorpay", gson.toJson(razorpay))
    }

    override suspend fun saveStore(store: Store) {
        sharedPrefsHelper.put("store", gson.toJson(store))
    }

    override suspend fun checkUserLoggedIn(): Boolean {
        return sharedPrefsHelper.hasKey("user")
    }

    override suspend fun getUser(): User {
        return gson.fromJson(sharedPrefsHelper.get("user", ""), User::class.java)
    }

    override suspend fun getStore(): Store {
        return gson.fromJson(sharedPrefsHelper.get("store", ""), Store::class.java)
    }


    override suspend fun getRazorPay(): Razorpay {
        return gson.fromJson(sharedPrefsHelper.get("razorpay", ""), Razorpay::class.java)
    }

    override suspend fun getBestSellingItems(): List<Item> {
        return database.inventoryDao().getBestSellingItems()
    }

    override suspend fun placeOrder(cartDetails: CartDetails): PlaceOrderResponse {
        val orderItems = cartDetails.cartItems
        val jsonArray = JSONArray()
        orderItems.forEach {
            val item = database.inventoryDao().getItem(it.itemCode)
            val orderJson = JSONObject()
            orderJson.put("itemId", it.itemId)
            orderJson.put("itemName", it.itemName)
            orderJson.put("itemPrice", it.itemPrice)
            orderJson.put("category", item.category)
            orderJson.put("quantity", it.quantity)
            orderJson.put("unit", item.unit)
            orderJson.put("totalAmount", it.itemPrice * it.quantity)
            orderJson.put("image", item.image)
            orderJson.put("netAmount", it.itemPrice * it.quantity)
            jsonArray.put(orderJson)
        }
        val jsonReq = JSONObject()
        val user = getUser()
        jsonReq.put("orderItems", jsonArray)
        jsonReq.put("customerId", user.customerId)
        jsonReq.put("storeId", getStore().storeId)
        jsonReq.put("totalAmount", cartDetails.totalBill)
        jsonReq.put("timestamp", Date().getFormattedDateInIso())
        if (user.addresses.isNotEmpty()) {
            user.addresses.forEach {
                val address = gson.toJson(it, Address::class.java)
                jsonReq.put("address", JSONObject(address))
            }
        }
        val body: RequestBody = jsonReq.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.placeOrder(Constants.API_KEY, body)
    }

    override suspend fun confirmOrder(paymentMetaData: PaymentMetaData): ConfirmPaymentResponse {

        val jsonReq = JSONObject()
        val user = getUser()
        jsonReq.put("orderNo", paymentMetaData.orderNo)
        jsonReq.put("customerId", user.customerId)
        jsonReq.put("razorpayId", paymentMetaData.razorpayId)
        jsonReq.put("razorpay_order_id", paymentMetaData.razorpayId)
        jsonReq.put("razorpay_payment_id", paymentMetaData.razorpayPaymentID)
        jsonReq.put("razorpay_signature", paymentMetaData.razorpayId)
        val body: RequestBody = jsonReq.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return apiService.confirmOrder(Constants.API_KEY, body)
    }

    override suspend fun clearInventory(): Flow<Boolean> {
        return flow {
            database.inventoryDao().clearItems();
            database.inventoryDao().clearCart();
            database.inventoryDao().clearCategories();
            database.inventoryDao().clearSubcategories();
            database.inventoryDao().clearSections();
            sharedPrefsHelper.clear()
            emit(true)
        }
    }

    override suspend fun clearCart(): Flow<Boolean> {
        return flow {
            database.inventoryDao().clearCart();
            emit(true)
        }
    }
}