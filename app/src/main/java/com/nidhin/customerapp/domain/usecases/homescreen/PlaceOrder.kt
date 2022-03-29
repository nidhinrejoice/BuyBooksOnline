package com.nidhin.customerapp.domain.usecases.homescreen

import com.nidhin.customerapp.commons.Constants
import com.nidhin.customerapp.commons.Resource
import com.nidhin.customerapp.domain.model.CartDetails
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PlaceOrder @Inject constructor(
    private val homeRepository: HomeRepository

) {
    suspend operator fun invoke(cartDetails: CartDetails): Flow<Resource<PaymentMetaData>> = flow {
        try {
            emit(Resource.Loading())
            var response = homeRepository.placeOrder(cartDetails)
            if (response.success) {

                val user = homeRepository.getUser()
                val razorpay = homeRepository.getRazorPay()
                val paymentMetaData = PaymentMetaData(
                    appName = Constants.APP_NAME, razorpayKeyId = razorpay.key.toString(),
                    userEmail = user.email, userMobile = user.mobile, razorpayId = response.order.payments?.get(0)?.razorpay?.id.toString(),
                    amount = cartDetails.payableAmount,
                    orderNo = response.order.orderNo.toString(), razorpayPaymentID = ""
                )
                emit(Resource.Success(paymentMetaData))
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