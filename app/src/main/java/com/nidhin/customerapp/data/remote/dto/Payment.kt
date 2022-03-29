package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Payment(
    val amount: Int,
    val paymentType: String?,
    val razorpay: Razorpay?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Razorpay::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(amount)
        parcel.writeString(paymentType)
        parcel.writeParcelable(razorpay, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Payment> {
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }
}