package com.alexpershin.githubrepos.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.ServerResult
import com.alexpershin.githubrepos.ui.state.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.alexpershin.githubrepos.model.GithubRepositoryEntity


/**
 * This class is created to fetch available repos from server sequently with pagination and
 * populate recyclerView
 */

class GithubRepositoryDataSource(
    private val apiRepository: ApiRepository,
    private var screenState: MutableLiveData<ScreenState>,
    private val ioScope: CoroutineScope,
    private val organizationQuery: String,
    private val  pageSize: Int

) : PageKeyedDataSource<Int, GithubRepositoryEntity>() {

    /**
     * loadInitial - fetches first page of available repos
     */

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubRepositoryEntity>
    ) {
        ioScope.launch {
            try {
                val serverResult =
                    apiRepository.getReposByOrganization(
                        organizationQuery,
                        INITIAL_PAGE,
                        pageSize
                    )
                when (serverResult) {
                    is ServerResult.Success -> {
                        val response = serverResult.data
                        val nextPage = INITIAL_PAGE + 1
                        val hasData = response.isNotEmpty()

                        if (hasData) {
                            screenState.postValue(ScreenState.Success)

                        } else {
                            screenState.postValue(ScreenState.EmptyResult)
                        }

                        callback.onResult(response, null, nextPage)

                    }
                    is ServerResult.Error -> {
                        screenState.postValue(
                            ScreenState.Error(
                                message = serverResult.message,
                                resId = serverResult.resId
                            )
                        )
                    }
                    is ServerResult.InternetError -> {
                        screenState.postValue(ScreenState.InternetError)
                    }
                }

            } catch (exception: Exception) {
                screenState.postValue(ScreenState.Error(exception.localizedMessage))
            }

        }
    }
    /**
     * loadAfter - sequentially fetches next pages
     */

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GithubRepositoryEntity>
    ) {
        ioScope.launch {
            val page = params.key
            val serverResult = apiRepository.getReposByOrganization(organizationQuery, page, pageSize)

            when (serverResult) {
                is ServerResult.Success -> {
                    val nextPage = if (serverResult.data.isNullOrEmpty()) 0 else params.key + 1
                    val response = serverResult.data
                    val hasData = response.isNotEmpty()

                    if (hasData) {
                        screenState.postValue(ScreenState.Success)

                    } else {
                        screenState.postValue(ScreenState.EmptyResult)
                    }

                    callback.onResult(response, nextPage)
                }
                is ServerResult.Error -> {
                    screenState.postValue(
                        ScreenState.Error(
                            message = serverResult.message,
                            resId = serverResult.resId
                        )
                    )
                }
                is ServerResult.InternetError -> {
                    screenState.postValue(ScreenState.InternetError)
                }
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubRepositoryEntity>) {}

    companion object {
        private const val INITIAL_PAGE = 0
    }
}