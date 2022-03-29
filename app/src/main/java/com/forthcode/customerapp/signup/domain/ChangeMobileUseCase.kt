package com.forthcode.customerapp.signup.domain

import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.signup.data.SignupRepository
import com.forthcode.customerapp.signup.data.SignupRequest
import javax.inject.Inject

class ChangeMobileUseCase  @Inject constructor(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke() =
        signupRepository.changeNumber()
}