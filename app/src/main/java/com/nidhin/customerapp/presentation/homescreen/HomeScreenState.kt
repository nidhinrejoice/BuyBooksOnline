package com.nidhin.customerapp.presentation.homescreen

import com.nidhin.customerapp.data.remote.dto.Order
import com.nidhin.customerapp.data.remote.dto.Store
import com.nidhin.customerapp.data.remote.dto.User
import com.nidhin.customerapp.domain.model.*

data class HomeScreenState(
    val loading : Boolean = false,
    val loadingMessage : String = "",
    val errorMessage : String = "",
    val placingOrder : Boolean = false,
    val store: Store? = null,
    val user: User? = null,
    val items: List<Item> = listOf(),
    val bestSellingItems: List<Item> = listOf(),
    val categories: List<Category> = listOf(),
    val subcategories: List<SubCategory> = listOf(),
    val sections: List<Section> = listOf(),
    val cartDetails: CartDetails? = null,
    val orders: List<Order> = listOf()
)