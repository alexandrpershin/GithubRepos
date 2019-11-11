package com.alexpershin.githubrepos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alexpershin.githubrepos.datasource.GithubRepositoryDataSource
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository
import com.alexpershin.githubrepos.ui.state.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * This class is responsible for providing data about Github repositories from server to view, eg. Activity
 * [databaseRepository] is optional object which can be used for storing fetched data from server
 * [apiRepository] is object which fetches data from server
 * [ioScope] allows to fetch data from server with coroutine scope
 * [GithubRepositoryDataSource] is responsible for loading data sequantly with pagination
 * [screenState] is liveData object which contains current state, see [ScreenState]
 * [config] is configuration for PagedListAdapter
* */

class MainActivityViewModel(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private var job = Job()

    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    val screenState: MutableLiveData<ScreenState> by lazy {
        MutableLiveData<ScreenState>()
    }

    private var config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
        .setEnablePlaceholders(true)
        .build()

    /**
     * Loads data from server.
     * [organizationQuery] by default equal to [DEFAULT_QUERY], change it in order to load other Github repositories, for eg. 'google'
    * */

    fun fetchGithubRepositories(organizationQuery:String = DEFAULT_QUERY) : LiveData<PagedList<GithubRepositoryEntity>> = initializeFilteredDataSource(organizationQuery).build()

    private fun initializeFilteredDataSource(organizationQuery: String): LivePagedListBuilder<Int, GithubRepositoryEntity> {
        screenState.postValue(ScreenState.Loading)

        val dataSourceFactory = object : DataSource.Factory<Int, GithubRepositoryEntity>() {
            override fun create(): DataSource<Int, GithubRepositoryEntity> {
                return GithubRepositoryDataSource(
                    apiRepository,
                    screenState,
                    ioScope,
                    organizationQuery,
                    pageSize = PAGE_SIZE
                )
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_LOAD_SIZE_HINT = 0
        private const val DEFAULT_QUERY = "square"
    }
}