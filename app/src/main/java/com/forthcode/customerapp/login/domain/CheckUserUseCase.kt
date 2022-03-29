package com.forthcode.customerapp.login.domain

import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val loginRepository: ILoginRepository
) {
    suspend operator fun invoke() =
        loginRepository.checkUser()
}