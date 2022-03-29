package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Metadata(
    val appVersion: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appVersion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Metadata> {
        override fun createFromParcel(parcel: Parcel): Metadata {
            return Metadata(parcel)
        }

        override fun newArray(size: Int): Array<Metadata?> {
            return arrayOfNulls(size)
        }
    }
}