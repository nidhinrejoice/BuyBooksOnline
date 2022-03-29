package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Razorpay(
    val key: String?,
    val secret: String?,
    val id: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(secret)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Razorpay> {
        override fun createFromParcel(parcel: Parcel): Razorpay {
            return Razorpay(parcel)
        }

        override fun newArray(size: Int): Array<Razorpay?> {
            return arrayOfNulls(size)
        }
    }
}