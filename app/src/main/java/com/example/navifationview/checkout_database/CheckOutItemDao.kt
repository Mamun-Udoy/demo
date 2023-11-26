package com.example.navifationview.checkout_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.navifationview.entities.ExpenseInfo

@Dao
interface CheckOutItemDao {

    @Insert
    fun insertCheckoutItem(checkOutItem: CheckOutItem)

    @Delete
    fun deleteCheckoutItem(checkOutItem: CheckOutItem)

    @Query(value = "SELECT * FROM checkoutitem ")
    fun readCheckoutItem(): List<CheckOutItem>?

    @Query("SELECT COUNT(id) FROM CheckOutItem")
    fun getCheckoutItemsSize(): Int

    @Query("DELETE FROM CheckOutItem")
    fun deleteAllCheckoutItems()

}