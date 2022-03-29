package com.nidhin.customerapp.domain.usecases.login

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.repository.UserLoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerifyOtp @Inject constructor(
    private val userLoginRepository: UserLoginRepository
) {
    suspend operator fun invoke(otp: String, mobile: String): Flow<Resource<String>> = flow {
        try {
            when {
                otp.isEmpty() -> {
                    emit(Resource.Error("OTP can't be empty"))
                }
                otp.length < 4 -> {
                    emit(Resource.Error("OTP should be 4 digits"))
                }
                else -> {
                    emit(Resource.Loading("Verifying otp..."))
                    val response = userLoginRepository.verifyOtp(otp, mobile)
                    if (response.success) {
                        userLoginRepository.loginUser(response.user)
                        emit(Resource.Success(response.message))
                    } else {
                        emit(Resource.Error(response.message))
                    }
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