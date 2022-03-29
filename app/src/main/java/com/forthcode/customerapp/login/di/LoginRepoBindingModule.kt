package com.forthcode.customerapp.login.di

import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.login.data.LoginRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class LoginRepoBindingModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(
        loginRepository: LoginRepository
    ): ILoginRepository

}