package com.alexpershin.githubrepos.di

import com.alexpershin.githubrepos.MyApplication
import com.alexpershin.githubrepos.di.component.ActivityComponent
import com.alexpershin.githubrepos.di.component.AppComponent
import com.alexpershin.githubrepos.di.component.DaggerActivityComponent
import com.alexpershin.githubrepos.di.component.DaggerAppComponent
import com.alexpershin.githubrepos.di.module.ActivityViewModelModule
import com.alexpershin.githubrepos.di.module.AppModule
import com.alexpershin.githubrepos.ui.activity.MainActivity

/**
 * This is helper class for dependency injection
 **/

class Injector private constructor() {

    private var activityComponent: ActivityComponent? = null
    private var appComponent: AppComponent? = null

    companion object {
        val instance: Injector by lazy {
            Injector()
        }
    }

    fun initialiseAppComponent(application: MyApplication) {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(application))
                .build()
        }
        appComponent!!.inject(application)
    }

    fun initActivityComponent(activity: MainActivity) {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent
                .builder()
                .activityViewModelModule(ActivityViewModelModule(activity))
                .appComponent(appComponent)
                .build()
        }
        activityComponent?.inject(activity = activity)
    }

    fun releaseActivityComponent() {
        activityComponent = null
    }


}