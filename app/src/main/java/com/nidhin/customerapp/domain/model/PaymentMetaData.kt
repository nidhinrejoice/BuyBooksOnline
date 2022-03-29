package com.nidhin.customerapp.domain.model

import android.os.Parcel
import android.os.Parcelable

data class PaymentMetaData(
    val orderNo : String,
    val razorpayId : String,
    val razorpayKeyId : String,
    var razorpayPaymentID : String,
    val appName : String,
    val userEmail : String,
    val userMobile : String,
    val amount : Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(orderNo)
        parcel.writeString(razorpayId)
        parcel.writeString(razorpayKeyId)
        parcel.writeString(razorpayPaymentID)
        parcel.writeString(appName)
        parcel.writeString(userEmail)
        parcel.writeString(userMobile)
        parcel.writeFloat(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentMetaData> {
        override fun createFromParcel(parcel: Parcel): PaymentMetaData {
            return PaymentMetaData(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMetaData?> {
            return arrayOfNulls(size)
        }
    }
}
