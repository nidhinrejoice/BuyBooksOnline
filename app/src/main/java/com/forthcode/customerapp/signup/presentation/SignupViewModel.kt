package com.forthcode.customerapp.signup.presentation

import android.util.Patterns
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forthcode.customerapp.login.data.ApiResponse
import com.forthcode.customerapp.signup.domain.ChangeMobileUseCase
import com.forthcode.customerapp.signup.domain.GetMobileUseCase
import com.forthcode.customerapp.signup.domain.SignupUseCase
import com.mvvmclean.trendingrepos.commons.UiStateViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.util.regex.Pattern
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val getMobileUseCase: GetMobileUseCase,
    private val changeMobileUseCase: ChangeMobileUseCase
) : UiStateViewModel() {

    val userMobile: LiveData<String>
        get() = _userMobile
    private var _userMobile: MutableLiveData<String> = MutableLiveData()
    val changeMobile: LiveData<Boolean>
        get() = _changeMobile
    private var _changeMobile: MutableLiveData<Boolean> = MutableLiveData()
    val toast: LiveData<String>
        get() = _toast
    private var _toast: MutableLiveData<String> = MutableLiveData()
    val pDialog: LiveData<String>
        get() = _pDialog
    private var _pDialog: MutableLiveData<String> = MutableLiveData()
    val onSignup: LiveData<ApiResponse>
        get() = _onSignup
    private var _onSignup: MutableLiveData<ApiResponse> = MutableLiveData()

    fun getMobile() {
        viewModelScope.launch(handler) {
            try {
                getMobileUseCase().collect { mobile ->
                    if (mobile?.isNotEmpty() == true)
                        _userMobile.value = mobile
                }
            } catch (e: Exception) {

            }
        }
    }

    fun changeMobile() {
        viewModelScope.launch(handler) {
            try {
                changeMobileUseCase().collect {
                    _changeMobile.value = it
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun signUp(firstName:String,lastName:String,email:String,mobile:String) {
        _pDialog.value = "Creating your account with us..."
        val signupRequest = JSONObject()
        signupRequest.put("mobile",mobile)
        signupRequest.put("email",email)
        signupRequest.put("firstName",firstName)
        signupRequest.put("lastName",lastName)
        signupRequest.put("password","password")
        viewModelScope.launch(handler) {
            try {
                signupUseCase(signupRequest).collect {
                    _pDialog.value = ""
                    if(it.success){
                        _toast.value = "Welcome, "+firstName
                    }else{
                        _toast.value = it.message
                    }
                    _onSignup.value = it
                }
            } catch (e: Exception) {
                _pDialog.value = ""
                _toast.value = e.message
            }
        }
    }

    fun validate(firstName:String,lastName:String,email:String,mobile:String) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email) .matches()){
            _toast.value = "Invalid email"
            return
        }
        if(firstName.length<3){
            _toast.value = "Invalid First Name"
            return
        }
        if(lastName.isEmpty()){
            _toast.value = "Invalid Last Name"
            return
        }
        if (!Pattern.matches("[a-zA-Z]*",firstName+lastName) ){
            _toast.value = "Invalid First Name or Last Name"
            return
        }
        signUp(firstName, lastName, email, mobile)
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }

}