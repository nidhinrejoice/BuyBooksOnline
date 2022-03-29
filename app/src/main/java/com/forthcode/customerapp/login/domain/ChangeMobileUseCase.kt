package com.forthcode.customerapp.login.domain

import com.forthcode.customerapp.login.data.LoginRepository
import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.signup.data.SignupRepository
import com.forthcode.customerapp.signup.data.SignupRequest
import javax.inject.Inject

class ChangeMobileUseCase  @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() =
        loginRepository.changeNumber()
}