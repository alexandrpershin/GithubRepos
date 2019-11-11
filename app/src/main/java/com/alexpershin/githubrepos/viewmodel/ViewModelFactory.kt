package com.alexpershin.githubrepos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository

class ViewModelFactory(
    private var databaseRepository: DatabaseRepository? = null,
    private var apiRepository: ApiRepository? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            //MainActivityViewModel
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                requireNotNull(databaseRepository) { "databaseRepository couldn't be null" }
                requireNotNull(apiRepository) { "apiRepository couldn't be null" }

                MainActivityViewModel(apiRepository!!, databaseRepository!!) as T
            }

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

