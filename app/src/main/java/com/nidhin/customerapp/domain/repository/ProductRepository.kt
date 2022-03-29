package com.nidhin.customerapp.domain.repository

import com.nidhin.customerapp.domain.model.CartItem
import com.nidhin.customerapp.domain.model.Item

interface ProductRepository {

    suspend fun getProductDetails(itemCode: String): Item
    suspend fun getCartItemDetails(itemCode: String): CartItem
    suspend fun addToCart(item: Item): CartItem
    suspend fun minusFromCart(item: Item): CartItem
    suspend fun getProductsFromCategory(catId: Int): List<Item>
    suspend fun getCartItems(): List<CartItem>
}