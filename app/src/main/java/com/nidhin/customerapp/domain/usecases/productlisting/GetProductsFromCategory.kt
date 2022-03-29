package com.nidhin.customerapp.domain.usecases.productlisting

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.ListingItem
import com.nidhin.customerapp.domain.repository.ProductRepository
import com.nidhin.customerapp.presentation.productlisting.ProductListingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsFromCategory @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(catId: Int): Flow<Resource<ProductListingState>> = flow {
        val items = productRepository.getProductsFromCategory(catId)
        var category = ""
        val listingItems = items.map {

            val cartItem = productRepository.getCartItemDetails(it.itemCode)
            var qty = 0
            if (cartItem != null) {
                qty = cartItem.quantity
            }
            if(category.isEmpty()){
                category = it.category
            }
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
                quantity = qty
            )
        }
        emit(Resource.Success(ProductListingState(items = listingItems, category = category)))
    }
}