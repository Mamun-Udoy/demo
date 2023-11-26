package com.example.navifationview.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User_Profile")
data class UserProfile(

    @PrimaryKey(autoGenerate = true) var id:Long=0,
    var username:String?=null,
    var usereamil:String?=null,
    var userphonenumber:String?=null

)
