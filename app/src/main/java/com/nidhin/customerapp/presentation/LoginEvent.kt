package com.nidhin.customerapp.presentation

sealed class LoginEvent {
    data class SendOtp(val mobile:String):LoginEvent()
    data class VerifyOtp(val otp:String, val mobile: String):LoginEvent()
    object ChangeNumber : LoginEvent()
    data class ResendOtp(val mobile:String):LoginEvent()
}