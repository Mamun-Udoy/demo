package com.example.navifationview.userViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navifationview.checkout_database.CheckOutItem
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.entities.UserProfile
import com.example.navifationview.user_data_base.UserDatabase
import com.google.gson.Gson

class UserViewModel : ViewModel() {

    val highestExpenseLiveData = MutableLiveData<Long>()

    val expenseAmount = MutableLiveData<List<Long>>()

    val expenseSum = MutableLiveData<Long>()

    val userProfileData = MutableLiveData<UserProfile>()



    // insert, delete and read data for checkout item

    fun deleteAllCheckoutItems( context: Context){
        val dao = UserDatabase.getDatabase(context)?.CheckOutItemDao()
        dao?.deleteAllCheckoutItems()
    }

    fun getCheckoutItemsSize(context: Context): Int? {
        val dao = UserDatabase.getDatabase(context)?.CheckOutItemDao()
        val data = dao?.getCheckoutItemsSize()
        return data
    }

    fun readCheckoutItem(context: Context): List<CheckOutItem> {

        val dao = UserDatabase.getDatabase(context)?.CheckOutItemDao()
        val data = dao?.readCheckoutItem() ?: emptyList()
        Log.d("checkoutitem", "read checkoutitem data: data: ${Gson().toJson(data)}")
        return data

    }


    fun insertCheckoutItem(checkOutItem: CheckOutItem, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.CheckOutItemDao()
        dao?.insertCheckoutItem(checkOutItem)

    }

    fun deleteChekcoutItem(checkOutItem: CheckOutItem, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.CheckOutItemDao()
        dao?.deleteCheckoutItem(checkOutItem)

    }

    fun getUserProfile(context: Context): UserProfile? {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        val dataList = dao?.readUserData()
        if (dataList?.isNotEmpty() == true) {
            Log.d("checkuser", "check the value inserted: ${Gson().toJson(dataList.last())}")
            userProfileData.value = dataList.last()
            return dataList.last()
        }
        return null
    }

    // this is for inserting userprofile in room database
    fun insertUserData(userProfile: UserProfile, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        dao?.useradd(userProfile)
        getUserProfile(context)

    }

    //this last 4 query for showing the value recyclerview doing read, insert, update, delete and show the most maximum in a button

    fun insertData(expenseInfo: ExpenseInfo, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        dao?.add(expenseInfo)
        Log.d("insert_db", "insertData: result: hi")
    }

    fun deleteData(expenseInfo: ExpenseInfo, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        dao?.deleteData(expenseInfo)

    }

    fun updateData(expenseInfo: ExpenseInfo, context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        dao?.updateData(expenseInfo)
    }
//    fun getHighestExpense(expenseInfo: ExpenseInfo,context: Context){
//        val dao = UserDatabase.getDatabase(context)?.expenseDao()
//        dao?.getHighestExpense(expenseInfo)
//
//    }

    fun getHighestExpenseAmount(context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        val result = dao?.getHighestExpenseAmount() ?: 0L
        highestExpenseLiveData.value = result
    }

//    fun expenseamount(context: Context){
//        val dao =UserDatabase.getDatabase(context)?.expenseDao()
//        val data=dao?.expenseAmount()?: emptyList()
//        expenseAmount.value=data
//
//
//    }

    fun getsum(context: Context) {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        val data = dao?.getSum() ?: 0L
        expenseSum.value = data

    }

    public fun getAllData(context: Context): List<ExpenseInfo> {
        val dao = UserDatabase.getDatabase(context)?.expenseDao()
        val data = dao?.readAllData() ?: emptyList()
        Log.d("result_db", "getAllData: data: ${Gson().toJson(data)}")
        getHighestExpenseAmount(context)
        getsum(context)
//        expenseamount(context)

        return data
    }

}