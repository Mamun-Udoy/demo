package com.example.navifationview.user_data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navifationview.checkout_database.CheckOutItem
import com.example.navifationview.checkout_database.CheckOutItemDao
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.entities.UserProfile
import com.example.navifationview.expense_dao.ExpenseDao


@Database(entities = [ExpenseInfo::class,UserProfile::class, CheckOutItem::class], version = 9)
abstract class UserDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun CheckOutItemDao(): CheckOutItemDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
//            if (instance != null) {
//                return instance as UserDatabase
//            }
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "my_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                return instance
            }
//            synchronized(this) {
//                 instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDatabase::class.java,
//                    "expense_database"
//                )
//                     .allowMainThreadQueries()
//                     .fallbackToDestructiveMigration()
//                     .build()
//                 return instance as UserDatabase
//
//            }

            return instance

        }

    }
}