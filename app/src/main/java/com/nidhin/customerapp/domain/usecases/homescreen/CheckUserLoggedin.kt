package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckUserLoggedin @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            if(homeRepository.checkUserLoggedIn()){
                emit(Resource.Success(""))
            }else{
                emit(Resource.Error("User not logged in"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}