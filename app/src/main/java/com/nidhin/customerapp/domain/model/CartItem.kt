package com.nidhin.customerapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    val itemCode: String,
    val itemName: String,
    var quantity: Int,
    var timeAdded : Date = Calendar.getInstance().time
)
