package com.example.navifationview

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.FirebaseApp

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}