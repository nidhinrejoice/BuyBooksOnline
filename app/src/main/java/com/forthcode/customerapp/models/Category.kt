package com.forthcode.customerapp.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
class Category {
    @PrimaryKey
    @SerializedName("catId")
    @Expose
    var catId: Long = 0L

    @SerializedName("catName")
    @Expose
    var catName: String? = null

    @SerializedName("colorCode")
    @Expose
    var colorCode: String? = null
    var description: String? = null
    var priceList: String? = null

    @Ignore
    var totalAmount = 0f

    @Ignore
    constructor(catId: Long, catName: String?) {
        this.catId = catId
        this.catName = catName
    }

    @Ignore
    constructor(catId: Long, catName: String?, totalAmount: Float) {
        this.catId = catId
        this.catName = catName
        this.totalAmount = totalAmount
    }

    @Ignore
    constructor(catName: String?, totalAmount: Float) {
        this.catName = catName
        this.totalAmount = totalAmount
    }

    constructor()


}
