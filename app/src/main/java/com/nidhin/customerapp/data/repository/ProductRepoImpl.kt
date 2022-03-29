package com.nidhin.customerapp.data.repository

import com.nidhin.customerapp.data.remote.ApiService
import com.nidhin.customerapp.domain.model.CartItem
import com.nidhin.customerapp.domain.model.Item
import com.nidhin.customerapp.domain.repository.ProductRepository
import com.nidhin.customerapp.persistance.AppDatabase
import com.nidhin.customerapp.persistance.SharedPrefsHelper
import com.google.gson.Gson
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val sharedPrefsHelper: SharedPrefsHelper,
    private val gson: Gson
) : ProductRepository {
    override suspend fun getProductDetails(itemCode: String): Item {
        return database.inventoryDao().getItem(itemCode)
    }

    override suspend fun addToCart(item: Item): CartItem {
        var item = database.inventoryDao().getItem(item.itemCode)
        var cartItem = database.inventoryDao().getCartItem(item.itemCode)
        if (cartItem == null) {
            cartItem = CartItem(itemCode = item.itemCode, itemName = item.itemName, quantity = 1)
        } else {
            cartItem.quantity++
        }
        database.inventoryDao().addCartItem(cartItem)
        return cartItem
    }

    override suspend fun minusFromCart(item: Item): CartItem {
        var cartItem = database.inventoryDao().getCartItem(item.itemCode)
        if (cartItem == null) {
            cartItem = CartItem(itemCode = item.itemCode, itemName = item.itemName, quantity = 0)
        } else {
            if (cartItem.quantity > 0) {
                cartItem.quantity--
                database.inventoryDao().addCartItem(cartItem)
            } else {
                cartItem.quantity = 0
                database.inventoryDao().removeCartItem(item.itemCode)
            }
        }
        return cartItem
    }

    override suspend fun getCartItemDetails(itemCode: String): CartItem {
        return database.inventoryDao().getCartItem(itemCode)
    }

    override suspend fun getProductsFromCategory(catId: Int): List<Item> {
        return database.inventoryDao().getProductsFromCategory(catId)
    }
    override suspend fun getCartItems(): List<CartItem> {
        return database.inventoryDao().getCartItems()
    }
}