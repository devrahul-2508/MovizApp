package com.example.movizapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.movizapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            val i = Intent(
                this,
                MainActivity::class.java
            )
            //Intent is used to switch from one activity to another.
            startActivity(i)
            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, 1500)
    }
}