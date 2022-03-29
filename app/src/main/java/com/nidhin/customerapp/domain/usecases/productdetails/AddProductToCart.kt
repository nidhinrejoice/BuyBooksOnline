package com.nidhin.customerapp.domain.usecases.productdetails

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.ProductRepository
import com.nidhin.customerapp.presentation.productdetails.ProductDetailsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductToCart @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(itemCode: String): Flow<Resource<ProductDetailsState>> = flow {
        val item = productRepository.getProductDetails(itemCode)
        emit(Resource.Success(ProductDetailsState(item = item, cartItem = productRepository.addToCart(item))))
    }
}