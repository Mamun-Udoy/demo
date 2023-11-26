package com.example.navifationview.expense_dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.entities.UserProfile


@Dao
interface ExpenseDao {
    @Insert
    fun add(expenseInfo: ExpenseInfo)

    @Delete
    fun deleteData(expenseInfo: ExpenseInfo)

    @Update
    fun updateData(expenseInfo: ExpenseInfo)

    @Query(value = "SELECT * FROM daily_expense_record ")
    fun readAllData(): List<ExpenseInfo>?


    @Query("SELECT SUM(expenseAmount) FROM daily_expense_record")
    fun getSum():Long



//
//    @Query("SELECT * FROM daily_expense_record ORDER BY expenseAmount DESC LIMIT 1")
//    fun getHighestExpense

    @Query("SELECT MAX(expenseAmount) FROM daily_expense_record")
    fun getHighestExpenseAmount(): Long

//    @Query("SELECT expenseAmount FROM daily_expense_record")
//    fun expenseAmount():List<Long>







    // Profile
    @Insert
    fun useradd(userProfile: UserProfile)

    @Query(value="SELECT * FROM user_profile")
    fun readUserData():List<UserProfile>?

}