package com.forthcode.customerapp.login.domain

import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val loginRepository: ILoginRepository
) {
    suspend operator fun invoke(otp: String, mobile: String) =
        loginRepository.verifyOtp(otp, mobile)
}