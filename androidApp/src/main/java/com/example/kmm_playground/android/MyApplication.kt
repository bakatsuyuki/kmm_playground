package com.example.kmm_playground.android

import android.app.Application
import com.example.kmm_playground.commonStartKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        commonStartKoin()
    }
}