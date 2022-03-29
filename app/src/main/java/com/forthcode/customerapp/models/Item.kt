package com.forthcode.customerapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "items")
class Item {
    @SerializedName("itemId")
    @Expose
    @PrimaryKey
    var itemId = 0

    @Ignore
    var id = 0

    @SerializedName("available")
    @Expose
    var available: Boolean? = null

    @SerializedName("baseUnit")
    @Expose
    var baseUnit: Float? = null

    @SerializedName("catId")
    @Expose
    var catId = 0

    @SerializedName("catName")
    @Expose
    var catName: String? = null

    @SerializedName("colorCode")
    @Expose
    var colorCode: String? = null

    @SerializedName("comboItem")
    @Expose
    @Ignore
    var comboItem: Boolean? = false

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("itemName")
    @Expose
    var itemName: String? = null

    @SerializedName("itemPrice")
    @Expose
    var itemPrice: Float? = 0f

    @SerializedName("listName")
    @Expose
    var priceList: String? = null

    @SerializedName("subCatId")
    @Expose
    var subCatId = 0

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("serviceCharge")
    @Expose
    var serviceCharge: Float? = 0f

    @SerializedName("sgst")
    @Expose
    var sgst: Float? = 0f

    @SerializedName("cgst")
    @Expose
    var cgst: Float? = 0f

    @SerializedName("igst")
    var igst: Float? = 0f

    @SerializedName("utgst")
    @Expose
    var utgst: Float? = 0f

    @SerializedName("unit")
    @Expose
    var unit: String? = null

    @SerializedName("gstType")
    @Expose
    var gstType: String? = null

    @SerializedName("hsn")
    @Expose
    var hsn: String? = null
    var nosSold: Long? = 0L

}
