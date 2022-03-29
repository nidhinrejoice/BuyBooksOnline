package com.forthcode.customerapp.login.domain

import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val loginRepository: ILoginRepository
) {
    suspend operator fun invoke(mobile: String) =
        loginRepository.sendOtp(mobile)
}