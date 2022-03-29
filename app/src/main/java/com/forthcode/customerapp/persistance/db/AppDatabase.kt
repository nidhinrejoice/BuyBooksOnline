package com.mvvmclean.trendingrepos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.forthcode.customerapp.models.Category
import com.forthcode.customerapp.models.Item
import com.forthcode.customerapp.persistance.db.InventoryRepoDao

@Database(entities = [Item::class,Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao() : InventoryRepoDao
}