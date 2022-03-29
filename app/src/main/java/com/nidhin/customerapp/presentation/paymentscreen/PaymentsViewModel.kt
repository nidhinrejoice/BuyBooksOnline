package com.nidhin.customerapp.presentation.paymentscreen

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.domain.usecases.paymentscreen.ConfirmPayment
import com.nidhin.customerapp.domain.usecases.paymentscreen.GetPaymentRelatedInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val getPaymentRelatedInfo: GetPaymentRelatedInfo,
    private val confirmPayment: ConfirmPayment
) : UiStateViewModel() {

    val toast: LiveData<String>
        get() = _toast
    private val _toast: MutableLiveData<String> = MutableLiveData()


    val metadata: LiveData<PaymentMetaData>
        get() = _metadata
    private val _metadata: MutableLiveData<PaymentMetaData> = MutableLiveData()

    val success: LiveData<Boolean>
        get() = _success
    private val _success: MutableLiveData<Boolean> = MutableLiveData()

    private var job: Job? = null
    private lateinit var paymentMetaData: PaymentMetaData

    fun getDetails(paymentMetaData: PaymentMetaData) {
        this.paymentMetaData = paymentMetaData
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        job?.cancel()
    }

    fun confirmPaymentOrder(paymentId: String) {
        job?.cancel()
        paymentMetaData.razorpayPaymentID = paymentId
        job = viewModelScope.launch {
            confirmPayment(paymentMetaData).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _success.value = true
                    }
                    is Resource.Error -> {
                        _toast.value = result.message
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
}