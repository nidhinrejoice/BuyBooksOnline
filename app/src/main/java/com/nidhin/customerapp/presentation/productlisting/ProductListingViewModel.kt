package com.nidhin.customerapp.presentation.productlisting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.domain.usecases.productdetails.AddProductToCart
import com.nidhin.customerapp.domain.usecases.productdetails.MinusProductFromCart
import com.nidhin.customerapp.domain.usecases.productlisting.GetProductsFromCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(
    private val getProductsFromCategory: GetProductsFromCategory,
    private val addProductToCart: AddProductToCart,
    private val minusProductFromCart: MinusProductFromCart
) : UiStateViewModel() {

    val toast: LiveData<String>
        get() = _toast
    private val _toast: MutableLiveData<String> = MutableLiveData()


    private val _state = mutableStateOf(ProductListingState(items = listOf(), category = ""))
    val state: State<ProductListingState> = _state
    private var job: Job? = null

    var selectedCatId =0

    fun getProducts(catId : Int) {
        selectedCatId = catId
        job?.cancel()
        job = viewModelScope.launch {
            getProductsFromCategory(catId).collect { result->
                when(result){
                    is Resource.Success -> {
                        _state.value = result.data!!
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        job?.cancel()
    }

    fun addItem(itemCode: String) {
        job?.cancel()
        job = viewModelScope.launch {
            addProductToCart(itemCode).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        getProducts(selectedCatId)
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun minusItem(itemCode: String) {
        job?.cancel()
        job = viewModelScope.launch {
            minusProductFromCart(itemCode).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        getProducts(selectedCatId)
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
}