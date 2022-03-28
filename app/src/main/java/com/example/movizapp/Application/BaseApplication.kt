package com.example.movizapp.Application

import android.app.Application
import com.example.movizapp.di.ApplicationComponent
import com.example.movizapp.di.DaggerApplicationComponent

class BaseApplication:Application() {

    lateinit var applicationComponent:ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerApplicationComponent.builder().build()
    }
}