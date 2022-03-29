package com.forthcode.customerapp.home.domain

import com.forthcode.customerapp.home.data.HomeRepository
import javax.inject.Inject

class GetInventoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() =
        homeRepository.getInventoryCategories()
}