package com.nidhin.customerapp.presentation.login

data class LoginScreenState(
    val mobileEntered : String ="7204352588",
    val otpEntered : String ="",
//    val loadingMessage : String ="",
    val showMessage : String ="",
    val waitingForOtp : Boolean = false,
    val otpVerified : Boolean = false,
    val inProgress : Boolean = false,
    val retryOtpEnabled : Boolean = false,
    val timerMessage : String =""
)
