package com.forthcode.customerapp.signup.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthcode.customerapp.signup.domain.ChangeMobileUseCase
import com.forthcode.customerapp.signup.domain.GetMobileUseCase
import com.forthcode.customerapp.signup.domain.SignupUseCase
import com.forthcode.customerapp.signup.presentation.SignupViewModel

class SignupViewModelFactory(
    private val signupUseCase: SignupUseCase,
    private val getMobileUseCase: GetMobileUseCase,
    private val changeMobileUseCase: ChangeMobileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignupViewModel(signupUseCase,getMobileUseCase,changeMobileUseCase) as T
    }
}