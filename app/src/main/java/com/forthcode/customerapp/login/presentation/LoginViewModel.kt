package com.forthcode.customerapp.login.presentation

import androidx.lifecycle.*
import com.forthcode.customerapp.login.data.UserFlow
import com.forthcode.customerapp.login.domain.ChangeMobileUseCase
import com.forthcode.customerapp.login.domain.CheckUserUseCase
import com.forthcode.customerapp.login.domain.SendOtpUseCase
import com.forthcode.customerapp.login.domain.VerifyOtpUseCase
import com.mvvmclean.trendingrepos.commons.UiStateViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception
import java.util.regex.Pattern
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val changeMobileUseCase: ChangeMobileUseCase
) :
    UiStateViewModel() {

    val sendOtpFailedResponse: MutableLiveData<String> = MutableLiveData()
    val verifyOtpFailedResponse: MutableLiveData<String> = MutableLiveData()
    val loginResponse: LiveData<String>
        get() = _loginResponse
    private val _loginResponse: MutableLiveData<String> = MutableLiveData()
    val newUserFound: LiveData<String>
        get() = _newUserFound
    private val _newUserFound: MutableLiveData<String> = MutableLiveData()
    val userLoggedIn: LiveData<String>
        get() = _userLoggedIn
    private val _userLoggedIn: MutableLiveData<String> = MutableLiveData()
    val changeMobile: LiveData<Boolean>
        get() = _changeMobile
    private val _changeMobile: MutableLiveData<Boolean> = MutableLiveData()
    private var userMobile: String = ""

    fun validate(mobile: String): Boolean {
        return Pattern.matches("^[0-9-]+\$", mobile) && mobile.length > 9
    }

    fun sendOtp(mobile: String) {
        viewModelScope.launch(handler) {
            try {
                sendOtpUseCase(mobile).collect { res ->
                    if (res.success) {
                        _loginResponse.value = res.message
                        userMobile = mobile
                    } else {
                        sendOtpFailedResponse.value = res.message
                    }
                }
            } catch (e: Exception) {
                sendOtpFailedResponse.value = e.message ?: "Unknown error"
            }
//            var response = loginRepo.userLogin(mobile)
//            if (response.isSuccessful) {
//                if (response.body()?.success == true) {
//                    loginResponse.value = true
//                } else {
//                    loginFailedResponse.value = response.body()?.message
//                }
//            } else {
//                loginFailedResponse.value = response.errorBody()?.toString()
//            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch(handler) {
            try {
                verifyOtpUseCase(otp, userMobile).collect { userFlow ->
                    when (userFlow) {
                        UserFlow.NEWUSER ->
                            _newUserFound.value = userMobile
                        UserFlow.LOGGEDIN ->
                            _userLoggedIn.value = "Welcome back!"
                        UserFlow.INVALIDOTP ->
                            verifyOtpFailedResponse.value = "Invalid OTP"
                    }
                }
            } catch (e: Exception) {
                if ((e is HttpException)) {
                    val errorJson: JSONObject =
                        JSONObject((e as HttpException).response()?.errorBody()?.string())
                    verifyOtpFailedResponse.value = errorJson.getString("message")
                } else {
                    verifyOtpFailedResponse.value = e.message ?: "Unknown error"
                }
            }
        }
    }

    fun checkUser() {
        viewModelScope.launch(handler) {
            try {
                checkUserUseCase().collect { userFlow ->
                    when (userFlow) {
                        UserFlow.NEWUSER ->
                            _newUserFound.value = userMobile
                        UserFlow.LOGGEDIN ->
                            _userLoggedIn.value = ""
                    }
                }
            } catch (e: Exception) {
                verifyOtpFailedResponse.value = e.message ?: "Unknown error"
            }
        }
    }

    fun changeMobile() {
        viewModelScope.launch(handler) {
            try {
                changeMobileUseCase().collect { result ->
                    userMobile = ""
                    _changeMobile.value = result
                }
            } catch (e: Exception) {
                verifyOtpFailedResponse.value = e.message ?: "Unknown error"
            }
        }
    }

    fun cancelJobs() {

    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        cancelJobs()
    }

}