package com.forthcode.customerapp.login.di

import com.forthcode.customerapp.login.data.LoginRepository
import com.forthcode.customerapp.login.domain.ChangeMobileUseCase
import com.forthcode.customerapp.login.domain.CheckUserUseCase
import com.forthcode.customerapp.login.domain.SendOtpUseCase
import com.forthcode.customerapp.login.domain.VerifyOtpUseCase
import dagger.Module
import dagger.Provides

@Module
open class LoginModule {

    @Provides
    fun provideSendOtpUseCase(
        loginRepository: LoginRepository
    ): SendOtpUseCase =
        SendOtpUseCase(loginRepository)

    @Provides
    fun provideVerifyOtpResponse(
        loginRepository: LoginRepository
    ): VerifyOtpUseCase =
        VerifyOtpUseCase(loginRepository)

    @Provides
    fun provideCheckUserOtpUseCase(
        loginRepository: LoginRepository
    ): CheckUserUseCase =
        CheckUserUseCase(loginRepository)

    @Provides
    fun provideChangeMobileUseCase(
        loginRepository: LoginRepository
    ): ChangeMobileUseCase =
        ChangeMobileUseCase(loginRepository)
//
//    @Provides
//    fun provideLoginRepository(
//        apiService: ApiService,
//        sharedPrefsHelper: SharedPrefsHelper
//    ): LoginRepository = LoginRepository(apiService,sharedPrefsHelper)
//


}