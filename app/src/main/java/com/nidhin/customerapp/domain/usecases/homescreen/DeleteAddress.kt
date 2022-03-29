package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteAddress @Inject constructor(
    private val userRepository: UserRepository

) {
    suspend operator fun invoke(addressId: String): Flow<Resource<Boolean>> = flow {
        try {
            var response = userRepository.deleteAddress(addressId)
            if (response.success) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error(response.message))
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