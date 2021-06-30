package com.foodstory.liveat500px

import android.app.Application
import com.inthecheesefactory.thecheeselibrary.manager.Contextor

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize thing(s) here
        Contextor.getInstance().init(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}