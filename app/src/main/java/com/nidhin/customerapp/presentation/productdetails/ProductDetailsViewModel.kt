package com.nidhin.customerapp.presentation.productdetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.domain.usecases.productdetails.AddProductToCart
import com.nidhin.customerapp.domain.usecases.productdetails.GetProductDetails
import com.nidhin.customerapp.domain.usecases.productdetails.MinusProductFromCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetails: GetProductDetails,
    private val addProductToCart: AddProductToCart,
    private val minusProductFromCart: MinusProductFromCart
) : UiStateViewModel() {

    val toast: LiveData<String>
        get() = _toast
    private val _toast: MutableLiveData<String> = MutableLiveData()


    private val _state = mutableStateOf(ProductDetailsState())
    val state: State<ProductDetailsState> = _state
    private var job: Job? = null

    fun getDetails(itemCode: String) {
        job?.cancel()
        job = viewModelScope.launch {
            getProductDetails(itemCode).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

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
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

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
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }
}