package com.nidhin.customerapp.domain.usecases.paymentscreen

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPaymentRelatedInfo @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(razorPayId: String,amount:Float): Flow<Resource<PaymentMetaData>> = flow {
        try {
            emit(Resource.Loading())
            val razorpay = homeRepository.getRazorPay()
            val user = homeRepository.getUser()
            val paymentMetaData = PaymentMetaData(
                appName = Constants.APP_NAME, razorpayKeyId = razorpay.key.toString(),
                userEmail = user.email, userMobile = user.mobile, razorpayId = razorPayId, amount = amount,
                orderNo = "", razorpayPaymentID = ""
            )
            emit(Resource.Success(paymentMetaData))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("Internet not available"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}