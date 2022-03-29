package com.forthcode.customerapp.signup.di

import com.forthcode.customerapp.signup.data.SignupRepository
import com.forthcode.customerapp.signup.domain.ISignupRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SignupRepoBindingModule {

    @Singleton
    @Binds
    abstract fun bindSignupRepository(
        signupRepository: SignupRepository
    ): ISignupRepository

}