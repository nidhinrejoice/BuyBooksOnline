package com.nidhin.customerapp.presentation.productlisting

import com.nidhin.customerapp.domain.model.ListingItem

data class ProductListingState(
    val items: List<ListingItem> = listOf(),
    val category : String

)