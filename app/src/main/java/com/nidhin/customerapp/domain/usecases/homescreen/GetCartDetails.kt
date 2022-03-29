package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.CartDetails
import com.nidhin.customerapp.domain.model.ListingItem
import com.nidhin.customerapp.domain.repository.HomeRepository
import com.nidhin.customerapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartDetails @Inject constructor(
    private val productRepository: ProductRepository,
    private val homeRepository: HomeRepository

) {
    suspend operator fun invoke(): Flow<Resource<CartDetails>> = flow {
        val cartItems = productRepository.getCartItems().map { cartItem ->
            val it = productRepository.getProductDetails(cartItem.itemCode)
            ListingItem(
                itemId = it.itemId,
                itemCode = it.itemCode,
                category = it.category,
                catId = it.catId,
                subCategory = it.subCategory,
                subCategoryId = it.subCategoryId,
                displayName = it.displayName,
                image = it.image,
                itemName = it.itemName,
                itemPrice = it.itemPrice,
                specialPrice = it.specialPrice,
                newItem = it.newItem,
                section = it.section,
                available = it.available,
                quantity = cartItem.quantity
            )
        }
        val store = homeRepository.getStore()
        var user = homeRepository.getUser()
        if(user ==null){
            emit(Resource.Error(""))
        }
        val cartDetails = CartDetails()
        cartItems.forEach {
            cartDetails.totalAmount += it.itemPrice * it.quantity
            cartDetails.payableAmount += it.specialPrice * it.quantity
            cartDetails.totalItems += it.quantity
        }
        cartDetails.deliveryCharges = (store.deliveryChargePerItem * cartDetails.totalItems)+ store.deliveryCharge
        cartDetails.totalDiscount = cartDetails.totalAmount - cartDetails.payableAmount
        cartDetails.totalBill = cartDetails.payableAmount + cartDetails.deliveryCharges
        cartDetails.cartItems = cartItems
        emit(Resource.Success(cartDetails))
    }
}