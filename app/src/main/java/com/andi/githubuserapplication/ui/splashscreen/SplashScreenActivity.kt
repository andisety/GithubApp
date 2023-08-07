package com.andi.githubuserapplication.ui.splashscreen


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.andi.githubuserapplication.MainActivity
import com.andi.githubuserapplication.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spllash_screen)

        supportActionBar?.hide()
        val time = 2000L
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java)).run {
                finish()
            }
        },time)
    }
}