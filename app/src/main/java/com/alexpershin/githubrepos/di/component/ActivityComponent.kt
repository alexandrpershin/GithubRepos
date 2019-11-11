package com.alexpershin.githubrepos.di.component

import com.alexpershin.githubrepos.di.module.ActivityViewModelModule
import com.alexpershin.githubrepos.di.scopes.ActivityScope
import com.alexpershin.githubrepos.ui.activity.MainActivity
import dagger.Component

/**
 * [ActivityComponent] is activity component for dependency injection using library Dagger2
 * */

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityViewModelModule::class]
)
interface ActivityComponent {
    fun inject(activity: MainActivity)
}
