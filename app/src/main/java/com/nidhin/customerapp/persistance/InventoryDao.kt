package com.nidhin.customerapp.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nidhin.customerapp.domain.model.*

@Dao
interface InventoryDao {

    @Query("SELECT * FROM items order by itemName")
    suspend fun allItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItems(items: List<Item?>?)

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()

    @Query("select * from items where itemCode is :code")
    suspend fun getItem(code: String): Item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubCategories(subCategories: List<SubCategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSections(sections: List<Section>)

    @Query("select * from items order by currentStock desc limit 10")
    suspend fun getBestSellingItems(): List<Item>

    @Query("select * from cart where itemCode = :itemCode")
    suspend fun getCartItem(itemCode: String): CartItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartItem(cartItem: CartItem)

    @Query("delete from cart where itemCode = :itemCode")
    suspend fun removeCartItem(itemCode: String)

    @Query("select * from items where catId =:catId")
    suspend fun getProductsFromCategory(catId: Int): List<Item>

    @Query("select * from cart where quantity>0 order by timeAdded")
    suspend fun getCartItems(): List<CartItem>

    @Query("delete from items")
    suspend fun clearItems()

    @Query("delete from sections")
    suspend fun clearSections()

    @Query("delete from subcategories")
    suspend fun clearSubcategories()

    @Query("delete from categories")
    suspend fun clearCategories()

    @Query("delete from cart")
    suspend fun clearCart()


}