package com.forthcode.customerapp.utils

import android.R
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ToastUtils {

    companion object {
        private var toast: Toast? = null
        fun makeToast(context: Context, message: String?) {
            try {
                toast?.cancel()
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                val toastView = toast?.view
                if (toastView == null) {
                    toast?.show()
                    return
                }
                val toastMessage = toastView.findViewById<TextView>(R.id.message)
                toastMessage.textSize = 20f
                toastMessage.text = message
                toastMessage.setTextColor(context.resources.getColor(R.color.primary_text_light))
                toastMessage.gravity = Gravity.CENTER
                toastMessage.setPadding(10, 0, 10, 0)
                toastView.setBackgroundColor(context.resources.getColor(R.color.primary_text_dark))
                toast?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}