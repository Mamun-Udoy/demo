package com.example.navifationview.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "Daily_Expense_Record")
data class ExpenseInfo(

    @PrimaryKey(autoGenerate = true) var id:Long=0,
    var expenseType:String,
    var expenseAmount:Long,
    val timeDate:String,
    var expenseDescription:String

)
