package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.data.remote.dto.User
import com.nidhin.customerapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAddresses @Inject constructor(
    private val userRepository: UserRepository

) {
    suspend operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            var addresses = userRepository.getAddresses()
            var user = userRepository.getUser()
            user.addresses = addresses
            emit(Resource.Success(user))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet not available"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}