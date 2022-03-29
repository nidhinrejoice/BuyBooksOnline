package com.forthcode.customerapp.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthcode.customerapp.login.domain.ChangeMobileUseCase
import com.forthcode.customerapp.login.domain.CheckUserUseCase
import com.forthcode.customerapp.login.presentation.LoginViewModel
import com.forthcode.customerapp.login.domain.SendOtpUseCase
import com.forthcode.customerapp.login.domain.VerifyOtpUseCase

class LoginViewModelFactory(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val checkUserUseCase: CheckUserUseCase,
    private val changeMobileUseCase: ChangeMobileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(sendOtpUseCase,verifyOtpUseCase,checkUserUseCase,changeMobileUseCase) as T
    }
}