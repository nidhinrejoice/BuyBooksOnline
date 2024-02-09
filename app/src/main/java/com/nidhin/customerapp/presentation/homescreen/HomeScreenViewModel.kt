package com.nidhin.customerapp.presentation.homescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.data.remote.dto.User
import com.nidhin.customerapp.domain.model.CartDetails
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.domain.usecases.homescreen.HomeScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homeScreenUseCases: HomeScreenUseCases
) : UiStateViewModel() {

    private val _gotoPayment = MutableSharedFlow<PaymentMetaData>()
    val gotoPayment = _gotoPayment.asSharedFlow()
    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    val loggedIn: LiveData<Boolean>
        get() = _loggedIn
    private val _loggedIn: MutableLiveData<Boolean> = MutableLiveData()

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var job: Job? = null

    fun checkUserLoggedIn() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.checkUserLoggedin().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _loggedIn.value = true
                        refreshInventory()
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        _loggedIn.value = false
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun refreshInventory() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.refreshInventory().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data!!
                        updateCart()
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        result.message?.let { _toast.emit(it) }
                        _state.value = _state.value.copy(
                            loading = false, errorMessage = result.message.toString()
                        )
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {
                        _state.value = HomeScreenState(
                            loading = true,
                            loadingMessage = "Refreshing inventory...",
                            errorMessage = ""
                        )
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
            homeScreenUseCases.addProductToCart(itemCode).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        updateCart()
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
            homeScreenUseCases.minusProductFromCart(itemCode).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        updateCart()
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun updateCart() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.getCartDetails().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data?.let {
                            _state.value.copy(
                                cartDetails = it,
                                orders = listOf()
                            )
                        }!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getPreviousOrders() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.getUserOrders().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data?.let {
                            _state.value.copy(
                                orders = _state.value.orders.plus(it)
                            )
                        }!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getProfileDetails() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.getUserOrders().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = result.data?.let {
                            _state.value.copy(
                                orders = _state.value.orders.plus(it)
                            )
                        }!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun updateUserDetails(user: User) {

    }

    fun getUserAddresses() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.getAddresses().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = result.data?.let {
                            _state.value.copy(
                                user = it
                            )
                        }!!
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun placeOrder(cartDetails: CartDetails) {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.placeOrder(cartDetails).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false, loadingMessage = ""
                        )
                        result.data?.let { _gotoPayment.emit(it) }
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false, loadingMessage = ""
                        )
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true, loadingMessage = "Placing the order"
                        )
                    }
                }
            }
        }
    }

    fun deleteAddress(addressId: String) {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.deleteAddress(addressId).collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _toast.emit("Address deleted")
                        getUserAddresses()
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        result.message?.let { _toast.emit(it) }
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun logout() {
        job?.cancel()
        job = viewModelScope.launch {
            homeScreenUseCases.logoutUser().collect { result ->
                when (result) {
                    is com.nidhin.customerapp.commons.Resource.Success -> {
                        _toast.emit("Logged out successfully")
                        checkUserLoggedIn()
                    }
                    is com.nidhin.customerapp.commons.Resource.Error -> {
                        result.message?.let { _toast.emit(it) }
                    }
                    is com.nidhin.customerapp.commons.Resource.Loading -> {

                    }
                }
            }
        }
    }
}