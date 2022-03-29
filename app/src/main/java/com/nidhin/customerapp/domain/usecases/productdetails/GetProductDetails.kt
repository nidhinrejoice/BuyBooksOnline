package com.nidhin.customerapp.domain.usecases.productdetails

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.CartItem
import com.nidhin.customerapp.domain.repository.ProductRepository
import com.nidhin.customerapp.presentation.productdetails.ProductDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetails @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(itemCode: String): Flow<Resource<ProductDetailsState>> = flow {
        val item = productRepository.getProductDetails(itemCode)
        var cartItem = productRepository.getCartItemDetails(itemCode)
        if (cartItem == null) {
            cartItem = CartItem(itemName = item.itemName, itemCode = itemCode, quantity = 0)
        }
        emit(Resource.Success(ProductDetailsState(item, cartItem)))
    }
}