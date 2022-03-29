package com.nidhin.customerapp.domain.usecases.login

import javax.inject.Inject

data class LoginUseCases @Inject constructor(
    val sendOtp: SendOtp,
    val reSendOtp: ReSendOtp,
    val verifyOtp: VerifyOtp
)
