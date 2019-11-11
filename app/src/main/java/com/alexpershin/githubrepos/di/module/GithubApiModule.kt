package com.alexpershin.githubrepos.di.module

import android.content.Context
import com.alexpershin.githubrepos.api.GithubApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GithubApiModule {

    @Singleton
    @Provides
    fun provideGithubApi(context: Context): GithubApi {
        return GithubApi.create(context)
    }
}