package com.forthcode.customerapp.home.domain

import com.forthcode.customerapp.home.data.HomeRepository
import javax.inject.Inject

class GetPincodeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() =
        homeRepository.getPincode()
}