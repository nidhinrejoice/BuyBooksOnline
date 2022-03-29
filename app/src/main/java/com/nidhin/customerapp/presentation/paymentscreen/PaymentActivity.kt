package com.nidhin.customerapp.presentation.paymentscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.Observer
import com.nidhin.customerapp.R
import com.nidhin.customerapp.domain.model.PaymentMetaData
import com.nidhin.customerapp.presentation.homescreen.HomeScreenActivity
import com.nidhin.customerapp.utils.showToast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


@ExperimentalAnimationApi
@AndroidEntryPoint
class PaymentActivity : AppCompatActivity(), PaymentResultListener {


    private val viewModel: PaymentsViewModel by viewModels()

    @OptIn(
        ExperimentalComposeUiApi::class,
        ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_payment)

//        Checkout.clearUserData(applicationContext)
        Checkout.preload(applicationContext)
        var paymentMetaData: PaymentMetaData? = intent.getParcelableExtra("paymentMetaData")
        viewModel.metadata.observe(this, this::startPayment)
        viewModel.success.observe(this) {
            startActivity(Intent(this, HomeScreenActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            finish()
        }
        paymentMetaData?.let {
            viewModel.getDetails(it)
            startPayment(paymentMetaData)
        }
    }

    private fun startPayment(paymentMetaData: PaymentMetaData) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID(paymentMetaData.razorpayKeyId)
        try {
            val options = JSONObject()
            options.put("name", paymentMetaData.appName)
            options.put("description", "Online Sale")
            options.put("order_id", paymentMetaData.razorpayId)
            options.put("send_sms_hash", true)
            options.put("allow_rotation", true)
            options.put("currency", "INR")
            options.put("amount", paymentMetaData.amount * 100)
            val preFill = JSONObject()
            preFill.put("email", paymentMetaData.userEmail)
            preFill.put("contact", paymentMetaData.userMobile)
            options.put("prefill", preFill)
            co.open(activity, options)
        } catch (e: Exception) {
            showToast("Error in payment: " + e.message)
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String) {
        try {
            viewModel.confirmPaymentOrder(razorpayPaymentID)
        } catch (e: java.lang.Exception) {
        }
    }

    override fun onPaymentError(code: Int, response: String) {
        try {
            Toast.makeText(this, "Payment failed: $code $response", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
        }
    }


}
