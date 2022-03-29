package com.nidhin.customerapp.data.remote.dto

import android.os.Parcel
import android.os.Parcelable

data class Order(
    val address: Address?,
    val customerId: Int,
    val deliveryCharge: Float,
    val discountAmount: Float,
    val events: List<Event>?,
    val metadata: Metadata?,
    val orderId: Int,
    val orderItems: List<OrderItem>?,
    val orderNo: String?,
    val orderStatus: String?,
    val orderType: String?,
    val paymentType: String?,
    val payments: List<Payment>?,
    val priceList: String?,
    val priceListModifiedAt: String?,
    val saleType: String?,
    val status: Status?,
    val storeId: Int,
    val timestampDateTime: String?,
    val totalAmount: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Address::class.java.classLoader),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.createTypedArrayList(Event),
        parcel.readParcelable(Metadata::class.java.classLoader),
        parcel.readInt(),
        parcel.createTypedArrayList(OrderItem),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Payment),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Status::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(address, flags)
        parcel.writeInt(customerId)
        parcel.writeFloat(deliveryCharge)
        parcel.writeFloat(discountAmount)
        parcel.writeTypedList(events)
        parcel.writeParcelable(metadata, flags)
        parcel.writeInt(orderId)
        parcel.writeTypedList(orderItems)
        parcel.writeString(orderNo)
        parcel.writeString(orderStatus)
        parcel.writeString(orderType)
        parcel.writeString(paymentType)
        parcel.writeString(priceList)
        parcel.writeString(priceListModifiedAt)
        parcel.writeString(saleType)
        parcel.writeParcelable(status, flags)
        parcel.writeInt(storeId)
        parcel.writeString(timestampDateTime)
        parcel.writeFloat(totalAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
