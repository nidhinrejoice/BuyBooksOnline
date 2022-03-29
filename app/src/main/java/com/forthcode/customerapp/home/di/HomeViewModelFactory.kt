package com.forthcode.customerapp.home.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forthcode.customerapp.home.domain.GetInventoryUseCase
import com.forthcode.customerapp.home.domain.GetPincodeUseCase
import com.forthcode.customerapp.home.presentation.HomeViewModel

class HomeViewModelFactory(
    private val getInventoryUseCase: GetInventoryUseCase,
    private val getPincodeUseCase: GetPincodeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(getInventoryUseCase,getPincodeUseCase) as T
    }
}