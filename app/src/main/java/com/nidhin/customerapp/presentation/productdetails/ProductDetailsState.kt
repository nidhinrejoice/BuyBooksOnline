package com.nidhin.customerapp.presentation.productdetails

import com.nidhin.customerapp.domain.model.CartItem
import com.nidhin.customerapp.domain.model.Item

data class ProductDetailsState(
    val item: Item? = null,
    val cartItem: CartItem? = null

)