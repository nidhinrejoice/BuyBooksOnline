package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.HomeRepository
import com.nidhin.customerapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LogoutUser @Inject constructor(
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository

) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> = flow {
        try {
            homeRepository.clearInventory().collect {
                emit(Resource.Success(true))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet not available"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}