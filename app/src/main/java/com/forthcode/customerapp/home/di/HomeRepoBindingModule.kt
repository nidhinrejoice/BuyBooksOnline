package com.forthcode.customerapp.home.di

import com.forthcode.customerapp.home.data.HomeRepository
import com.forthcode.customerapp.home.domain.IHomeRepository
import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.login.data.LoginRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class HomeRepoBindingModule {

    @Singleton
    @Binds
    abstract fun bindHomeRepository(
        homeRepository: HomeRepository
    ): IHomeRepository

}