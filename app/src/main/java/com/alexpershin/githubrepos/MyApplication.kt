package com.alexpershin.githubrepos

import android.app.Application
import com.alexpershin.githubrepos.di.Injector
import com.alexpershin.githubrepos.utils.NetworkUtils

class MyApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Injector.instance.initialiseAppComponent(this)
        NetworkUtils.instance.initialize(this)
    }

}