
package com.forthcode.customerapp.persistance.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.forthcode.customerapp.models.Category
import com.forthcode.customerapp.models.Item

@Dao
interface InventoryRepoDao {
    @Query("SELECT * FROM items")
    suspend fun allItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItems(items: List<Item?>?)

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()

    @Query("SELECT COUNT(*) FROM items")
    suspend fun isItemCacheAvailable(): Int

    @Query("SELECT * FROM categories")
    suspend fun allCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(items: List<Category?>?)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

}