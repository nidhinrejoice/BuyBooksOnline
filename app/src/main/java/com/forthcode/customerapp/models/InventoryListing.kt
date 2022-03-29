package com.forthcode.customerapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InventoryListing {
    @SerializedName("items")
    @Expose
    var items: List<Item?>? = null
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("categories")
    @Expose
    var categories: List<Category?>? = null
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("success")
    @Expose
    var success: Boolean? = null



}