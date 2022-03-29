package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Address(
    val _id: String? = "",
    val addressLine1: String?,
    val addressLine2: String?,
    val label: String?,
    val landmark: String?,
    val latitude: String? = "",
    val longitude: String? = "",
    val pincode: String?,
    val type: String?,
    val defaultAddress: Boolean?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(addressLine1)
        parcel.writeString(addressLine2)
        parcel.writeString(label)
        parcel.writeString(landmark)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(pincode)
        parcel.writeString(type)
        parcel.writeValue(defaultAddress)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}