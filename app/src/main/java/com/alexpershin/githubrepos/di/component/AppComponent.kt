package com.alexpershin.githubrepos.di.component

import android.content.Context
import com.alexpershin.githubrepos.MyApplication
import com.alexpershin.githubrepos.di.module.AppModule
import com.alexpershin.githubrepos.di.module.DatabaseModule
import com.alexpershin.githubrepos.di.module.GithubApiModule
import com.alexpershin.githubrepos.di.module.RepositoryModule
import com.alexpershin.githubrepos.persistence.LocalDatabase
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository
import dagger.Component
import javax.inject.Singleton


/**
 * [AppComponent] is application component for dependency injection using library Dagger2
 * */

@Singleton
@Component(
    modules = [AppModule::class, RepositoryModule::class, DatabaseModule::class, GithubApiModule::class]
)
interface AppComponent {
    fun inject(application: MyApplication)

    fun provideContext(): Context
    fun provideApiRepository(): ApiRepository
    fun provideDatabaseRepository(): DatabaseRepository
    fun provideDatabase(): LocalDatabase
}