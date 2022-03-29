package com.forthcode.customerapp.home.di

import com.forthcode.customerapp.home.data.HomeRepository
import com.forthcode.customerapp.home.domain.GetInventoryUseCase
import com.forthcode.customerapp.home.domain.GetPincodeUseCase
import dagger.Module
import dagger.Provides

@Module
open class HomeModule {

    @Provides
    fun provideGetInventoryUseCase(
        homeRepository: HomeRepository
    ): GetInventoryUseCase =
        GetInventoryUseCase(homeRepository)

    @Provides
    fun provideGetPincodeUseCase(
        homeRepository: HomeRepository
    ): GetPincodeUseCase =
        GetPincodeUseCase(homeRepository)


}