package com.example.manishweatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@splash_activity,MainActivity::class.java)
            startActivity(intent)
            finish()  // isaka matlab hai ki ek baar ake chala jayega
        }, 3000)


    }
}