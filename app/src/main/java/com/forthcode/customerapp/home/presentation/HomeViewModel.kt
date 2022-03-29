package com.forthcode.customerapp.home.presentation

import androidx.lifecycle.*
import com.forthcode.customerapp.home.domain.GetInventoryUseCase
import com.forthcode.customerapp.home.domain.GetPincodeUseCase
import com.forthcode.customerapp.models.Category
import com.mvvmclean.trendingrepos.commons.UiStateViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getInventoryUseCase: GetInventoryUseCase,
    private val getPincodeUseCase: GetPincodeUseCase
) :
    UiStateViewModel() {

    val pincode: LiveData<String>
        get() = _pincode
    private val _pincode: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String>
        get() = _toast
    private val _toast: MutableLiveData<String> = MutableLiveData()
    val categories: LiveData<List<Category>>
        get() = _categories
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()

    fun getPincode() {
        viewModelScope.launch(handler) {
            try {
                getPincodeUseCase().collect { result ->
                    _pincode.value = result
                    getInventory()

                }
            } catch (e: Exception) {
                _toast.value = e.message ?: "Unknown error"
            }
        }
    }

    fun getInventory() {
        viewModelScope.launch(handler) {
            try {
                getInventoryUseCase().collect { result ->
                    _categories.value = result
                }
            } catch (e: Exception) {
                _toast.value = e.message ?: "Unknown error"
            }
        }
    }

    fun cancelJobs() {

    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        cancelJobs()
    }

}