package com.forthcode.customerapp.productdetails.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.forthcode.customerapp.R
import com.forthcode.customerapp.persistance.db.InventoryRepoDao

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

    }
}