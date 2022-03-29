package com.forthcode.customerapp.login.data

import com.forthcode.customerapp.api.ApiService
import com.forthcode.customerapp.login.domain.ILoginRepository
import com.forthcode.customerapp.persistance.SharedPrefsHelper
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefsHelper: SharedPrefsHelper
) : ILoginRepository {

    var job: CompletableJob? = null
    val gson: Gson = Gson()

    override suspend fun sendOtp(mobile: String): Flow<ApiResponse> {
//        if (BuildConfig.DEBUG) {
//            val response = LoginResponse(true, "Otp sent to +91-" + mobile)
//            return flow { emit(response) }
//        }
//        job = Job()
        var jsonObject = JSONObject()
        jsonObject.put("userId", mobile)
        val body: RequestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        val loginResponse = LoginResponse(true,"Otp sent succesfully")
//        delay(1000L)
        return flow { emit(apiService.sendOtp(body)) }
//        return object : LiveData<LoginResponse>() {
//            override fun onActive() {
//                super.onActive()
//                job?.let { theJob ->
//                    CoroutineScope(IO + theJob).launch {
//                        val response = RetrofitBuilder.apiService.login(body)
//                        withContext(Main) {
//                            value = response
//                            theJob.complete()
//
//                        }
//                    }
//                }
//            }
//        }

    }

    override suspend fun verifyOtp(otp: String, mobile: String): Flow<UserFlow> {
//        if (BuildConfig.DEBUG) {
//            sharedPrefsHelper.put("userMobile", mobile)
//            return flow {
//                emit(UserFlow.NEWUSER)
//            }
//        }
        var jsonObject = JSONObject()
        jsonObject.put("userId", mobile)
        jsonObject.put("otp", otp)
        val body: RequestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return flow {
            val response = apiService.verifyOtp(body)
            if (response.success) {
                sharedPrefsHelper.put("userMobile", mobile)
                if (response.user != null) {
                    sharedPrefsHelper.put("user", gson.toJson(response.user))
                    emit(UserFlow.LOGGEDIN)
                } else {
                    emit(UserFlow.NEWUSER)
                }
            } else {
                emit(UserFlow.INVALIDOTP)
            }
        }
    }

    override suspend fun checkUser(): Flow<UserFlow> {
        return flow {
            if (sharedPrefsHelper.hasKey("user")) {
                emit(UserFlow.LOGGEDIN)
            } else if (sharedPrefsHelper.hasKey("userMobile")) {
                emit(UserFlow.NEWUSER)
            } else {
                emit(UserFlow.VERIFYOTP)
            }
        }
    }

    override suspend fun changeNumber(): Flow<Boolean> {
        return flow{
            sharedPrefsHelper.removeKey("userMobile")
            emit(true)
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }
}