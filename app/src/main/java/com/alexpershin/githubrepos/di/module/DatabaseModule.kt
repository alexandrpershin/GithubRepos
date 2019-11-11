package com.alexpershin.githubrepos.di.module

import android.content.Context
import com.alexpershin.githubrepos.persistence.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }
}