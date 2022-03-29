package com.forthcode.customerapp.signup.data

import com.forthcode.customerapp.api.ApiService
import com.forthcode.customerapp.login.data.ApiResponse
import com.forthcode.customerapp.persistance.SharedPrefsHelper
import com.forthcode.customerapp.signup.domain.ISignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefsHelper: SharedPrefsHelper
) : ISignupRepository {

    override suspend fun signUp(request: JSONObject): Flow<ApiResponse> {

        val body: RequestBody = request.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return flow {
            val res = apiService.signup(body)
            sharedPrefsHelper.put("user",request.toString());
            emit(res)
        }
    }

    override suspend fun getMobile(): Flow<String?> {
        return flow {
            emit(sharedPrefsHelper.get("userMobile", ""))
        }
    }

    override suspend fun changeNumber(): Flow<Boolean> {
        return flow {
            sharedPrefsHelper.removeKey("userMobile")
            emit(true)
        }
    }
}