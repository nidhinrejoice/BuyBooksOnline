package com.nidhin.customerapp.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nidhin.customerapp.domain.model.*

@Database(
    entities = [Item::class,Category::class,SubCategory::class,Section::class,CartItem::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}