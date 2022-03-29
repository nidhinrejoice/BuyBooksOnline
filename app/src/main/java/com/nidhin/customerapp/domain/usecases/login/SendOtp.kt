package com.nidhin.customerapp.domain.usecases.login

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.UserLoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendOtp @Inject constructor(
    private val userLoginRepository: UserLoginRepository
) {
    suspend operator fun invoke(mobile: String): Flow<Resource<String>> = flow {
        try {
            when {
                mobile.isEmpty() -> {
                    emit(Resource.Error("Mobile number can't be empty"))
                }
                mobile.length<10 -> {
                    emit(Resource.Error("Mobile number should be 10 digits"))
                }
                else -> {
                    emit(Resource.Loading("Sending otp to +91-$mobile..."))
                    val response = userLoginRepository.sendOtp(mobile)
                    emit(Resource.Success(response))
                }
            }
        } catch (e: HttpException) {
            val error = JSONObject(e.response()?.errorBody()?.string())
            emit(Resource.Error(error.getString("message") ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet not available"))
        } catch (e: Exception) {
            emit(Resource.Error("Something went wrong"))
        }
    }
}