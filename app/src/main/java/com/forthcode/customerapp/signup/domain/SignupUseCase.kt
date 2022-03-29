package com.forthcode.customerapp.signup.domain

import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.signup.data.SignupRepository
import com.forthcode.customerapp.signup.data.SignupRequest
import org.json.JSONObject
import javax.inject.Inject

class SignupUseCase  @Inject constructor(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(request: JSONObject) =
        signupRepository.signUp(request)
}