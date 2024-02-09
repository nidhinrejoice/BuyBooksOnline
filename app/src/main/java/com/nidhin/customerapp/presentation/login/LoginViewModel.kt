package com.nidhin.customerapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.commons.UiStateViewModel
import com.nidhin.customerapp.domain.usecases.login.LoginUseCases
import com.nidhin.customerapp.presentation.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : UiStateViewModel() {
    private var job: Job? = null
    private var timerJob: Job? = null
    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        timerJob?.cancel()
        job?.cancel()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SendOtp -> {
//                sendOtp(event.mobile)
                verifyOtp("1234", event.mobile)
            }
            is LoginEvent.VerifyOtp -> {
                timerJob?.cancel()
                verifyOtp(event.otp, event.mobile)

            }
            is LoginEvent.ChangeNumber -> {
                timerJob?.cancel()
                _state.value = LoginScreenState()
            }
            is LoginEvent.ResendOtp -> {
                timerJob?.cancel()
                resendOtp(event.mobile)
            }
        }
    }

    private fun sendOtp(mobile: String) {
        job?.cancel()
        job = viewModelScope.launch {
            loginUseCases.sendOtp(mobile).collect { result ->
                when (result) {
                    is Resource.Success-> {
                        _state.value = _state.value.copy(
                            showMessage = result.data!!, waitingForOtp = true,
                            inProgress = false
                        )
                        startTimer()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            showMessage = result.message ?: "Something went wrong",
                            inProgress = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = LoginScreenState(
                            showMessage = result.data ?: "Sending otp...", inProgress = true,
                            mobileEntered = mobile
                        )
                    }
                }
            }
        }
    }

    private fun resendOtp(mobile: String) {
        job?.cancel()
        job = viewModelScope.launch {
            loginUseCases.reSendOtp(mobile).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            showMessage = result.data!!, waitingForOtp = true,
                            inProgress = false
                        )
                        startTimer()
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            showMessage = result.message ?: "Something went wrong",
                            inProgress = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = LoginScreenState(
                            showMessage = result.data ?: "Sending otp...", inProgress = true,
                            mobileEntered = mobile, retryOtpEnabled = false
                        )
                    }
                }
            }
        }
    }

    private fun verifyOtp(otp: String, mobile: String) {
        job?.cancel()
        job = viewModelScope.launch {
            loginUseCases.verifyOtp(otp, mobile).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            showMessage = result.data!!, otpVerified = true,
                            inProgress = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            showMessage = result.message ?: "Something went wrong",
                            inProgress = false, otpVerified = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            showMessage = result.data ?: "Verifying otp...", inProgress = true,
                            mobileEntered = mobile
                        )
                    }
                }
            }
        }
    }

    val timerFlow = flow {
        val startingValue = 3
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            timerFlow.collectLatest {
                if (it == 0) {
                    _state.value =
                        _state.value.copy(timerMessage = " ", retryOtpEnabled = true)
                } else {
                    _state.value =
                        _state.value.copy(timerMessage = "Retry sending otp in $it seconds...")
                }
            }
        }
    }
}