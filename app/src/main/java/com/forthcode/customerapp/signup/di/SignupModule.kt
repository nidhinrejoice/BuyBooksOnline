package com.forthcode.customerapp.signup.di

import com.forthcode.customerapp.signup.data.SignupRepository
import com.forthcode.customerapp.signup.domain.ChangeMobileUseCase
import com.forthcode.customerapp.signup.domain.GetMobileUseCase
import com.forthcode.customerapp.signup.domain.SignupUseCase
import dagger.Module
import dagger.Provides

@Module
open class SignupModule {

    @Provides
    fun provideSignupUseCase(signupRepository: SignupRepository
    ): SignupUseCase =
        SignupUseCase(signupRepository)

    @Provides
    fun provideGetMobileUseCase(signupRepository: SignupRepository
    ): GetMobileUseCase =
        GetMobileUseCase(signupRepository)

    @Provides
    fun provideChangeMobileUseCase(signupRepository: SignupRepository
    ): ChangeMobileUseCase =
        ChangeMobileUseCase(signupRepository)


}