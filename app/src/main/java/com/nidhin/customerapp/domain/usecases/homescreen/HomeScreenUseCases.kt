package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.domain.usecases.productdetails.AddProductToCart
import com.nidhin.customerapp.domain.usecases.productdetails.MinusProductFromCart
import javax.inject.Inject

data class HomeScreenUseCases @Inject constructor(
    val checkUserLoggedin: CheckUserLoggedin,
    val refreshInventory: PopulateInventory,
    val addProductToCart: AddProductToCart,
    val minusProductFromCart: MinusProductFromCart,
    val getCartDetails: GetCartDetails,
    val getUserOrders: GetUserOrders,
    val getAddresses: GetAddresses,
    val deleteAddress: DeleteAddress,
    val placeOrder: PlaceOrder,
    val logoutUser: LogoutUser
)
