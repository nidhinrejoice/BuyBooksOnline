package com.nidhin.customerapp.domain.usecases.addaddress

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.data.remote.dto.Address
import com.nidhin.customerapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddAddress @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(address: Address): Flow<Resource<String>> = flow {
        try {
            when {
                address.addressLine1?.length!! < 10 -> {
                    emit(Resource.Error("Address line 1 should be 10 characters"))
                }
                address.addressLine2?.length!! < 10 -> {
                    emit(Resource.Error("Address line 2 should be 10 characters"))
                }
                address.landmark?.length!! < 6 -> {
                    emit(Resource.Error("Landmark line 1 should be 6 characters"))
                }
                address.type?.isEmpty() == true -> {
                    emit(Resource.Error("Address Type should be selected"))
                }
                address.pincode?.length!! < 6 -> {
                    emit(Resource.Error("Pincode should be 6 digits"))
                }
                address.label!!.length < 3 -> {
                    emit(Resource.Error("Label should be 3 characters"))
                }
                else -> {
                    emit(Resource.Loading("Creating the address..."))
                    emit(Resource.Success("Address added."))
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