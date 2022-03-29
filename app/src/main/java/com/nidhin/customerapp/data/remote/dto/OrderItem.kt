package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class OrderItem(
    val baseUnit: Float,
    val category: String?,
    val cess: Float,
    val cgst: Float,
    val gst: Float,
    val gstType: String?,
    val hsn: String?,
    val igst: Float,
    val image: String?,
    val itemId: Int,
    val itemName: String?,
    val itemPrice: Float,
    val netAmount: Float,
    val quantity: Int,
    val sgst: Float,
    val totalAmount: Float,
    val unit: String?,
    val utgst: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(baseUnit)
        parcel.writeString(category)
        parcel.writeFloat(cess)
        parcel.writeFloat(cgst)
        parcel.writeFloat(gst)
        parcel.writeString(gstType)
        parcel.writeString(hsn)
        parcel.writeFloat(igst)
        parcel.writeString(image)
        parcel.writeInt(itemId)
        parcel.writeString(itemName)
        parcel.writeFloat(itemPrice)
        parcel.writeFloat(netAmount)
        parcel.writeInt(quantity)
        parcel.writeFloat(sgst)
        parcel.writeFloat(totalAmount)
        parcel.writeString(unit)
        parcel.writeFloat(utgst)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderItem> {
        override fun createFromParcel(parcel: Parcel): OrderItem {
            return OrderItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderItem?> {
            return arrayOfNulls(size)
        }
    }
}