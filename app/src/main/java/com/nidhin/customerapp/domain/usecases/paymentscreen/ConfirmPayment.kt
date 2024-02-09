package com.nidhin.customerapp.domain.usecases.paymentscreen

import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConfirmPayment @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(data: PaymentMetaData): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val res = homeRepository.confirmOrder(data)
            if (res.success) {
                homeRepository.clearCart().collect {
                    emit(Resource.Success(true))
                }
            } else {
                emit(Resource.Error("Something went wrong"))
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