package com.alexpershin.githubrepos.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository
import com.alexpershin.githubrepos.viewmodel.MainActivityViewModel
import com.alexpershin.githubrepos.viewmodel.ViewModelFactory

import dagger.Module
import dagger.Provides

/**
 * This class is created for providing 'ViewModel' instances for activities
 * If activity doesn't have 'ViewModel' then pass @param [activity]  as 'null'
 * */

@Module
class ActivityViewModelModule(private val activity: AppCompatActivity?) {

    /**
     * Creates ViewModel for [MainActivityViewModel]
     * */

    @Provides
    fun provideMainActivityViewModel(databaseRepository: DatabaseRepository, apiRepository: ApiRepository): MainActivityViewModel {
        return ViewModelProvider(
            activity!!,
            ViewModelFactory(databaseRepository = databaseRepository, apiRepository = apiRepository)
        ).get(MainActivityViewModel::class.java)
    }

}