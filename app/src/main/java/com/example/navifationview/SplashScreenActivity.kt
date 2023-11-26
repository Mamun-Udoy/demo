package com.example.navifationview
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkBuilder
import com.example.navifationview.ui.login.LoginFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val shouldShowCustomSplash = sharedPreferences.getBoolean("showCustomSplash", true)

        if (shouldShowCustomSplash) {
            installSplashScreen()
            setContentView(R.layout.activity_splash_screen)
            lifecycleScope.launch {
                delay(2000)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()


            }
            sharedPreferences.edit().putBoolean("showCustomSplash", false).apply()


        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()


        }

    }
}