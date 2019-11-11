package com.alexpershin.githubrepos.di.module

import com.alexpershin.githubrepos.api.GithubApi
import com.alexpershin.githubrepos.persistence.LocalDatabase
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository
import com.alexpershin.githubrepos.utils.NetworkUtils
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideApiRepository(api: GithubApi): ApiRepository {
        return ApiRepository(api)
    }

    @Provides
    fun databaseRepository(localDatabase: LocalDatabase): DatabaseRepository {
        return DatabaseRepository(localDatabase)
    }
}