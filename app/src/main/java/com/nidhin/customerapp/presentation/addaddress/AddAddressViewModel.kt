package com.nidhin.customerapp.presentation.addaddress

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.data.remote.dto.Address
import com.nidhin.customerapp.domain.usecases.addaddress.AddAddress
import com.nidhin.customerapp.presentation.productdetails.ProductDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val addAddress: AddAddress
) : UiStateViewModel() {

    val toast: LiveData<String>
        get() = _toast
    private val _toast: MutableLiveData<String> = MutableLiveData()

    val progress: LiveData<String>
        get() = _progress
    private val _progress: MutableLiveData<String> = MutableLiveData()


    private val _state = mutableStateOf(ProductDetailsState())
    val state: State<ProductDetailsState> = _state
    private var job: Job? = null


    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }

    fun addAddress(
        addressLine1: String,
        addressLine2: String,
        landmark: String,
        pincode: String,
        label: String,
        type: String
    ) {
        val address = Address(
            addressLine1 = addressLine1, addressLine2 = addressLine2, landmark = landmark,
            label = label, pincode = pincode, type = type, defaultAddress = false
        )
        job?.cancel()
        job = viewModelScope.launch {
            addAddress.invoke(address).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _toast.value = result.message
                        _progress.value = ""
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        _toast.value = result.message
                        _progress.value = ""
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {
                        _progress.value = result.message

                    }
                }
            }
        }
    }

}