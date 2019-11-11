package com.alexpershin.githubrepos.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexpershin.githubrepos.R
import com.alexpershin.githubrepos.di.Injector
import com.alexpershin.githubrepos.model.GithubRepositoryEntity
import com.alexpershin.githubrepos.ui.adapter.GithubRepositoryAdapter
import com.alexpershin.githubrepos.ui.state.ScreenState
import com.alexpershin.githubrepos.utils.NetworkUtils
import com.alexpershin.githubrepos.utils.makeGone
import com.alexpershin.githubrepos.utils.makeVisible
import com.alexpershin.githubrepos.utils.stopRefreshing
import com.alexpershin.githubrepos.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mViewModel: MainActivityViewModel

    private lateinit var adapter: GithubRepositoryAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * Observer for updating PagedListAdapter with new data from server
     * */

    private val githubReposObserver = Observer<PagedList<GithubRepositoryEntity>> { pagedList ->
        adapter.submitList(pagedList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Github repositories"

        //Init activity dependencies
        Injector.instance.initActivityComponent(this)

        setupObservers()
        setupRecyclerView()
        setupSwipeToRefreshLayout()
    }

    /**
     * Force reload Github repositories from server
     * */

    private fun setupSwipeToRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.fetchGithubRepositories().observe(this, githubReposObserver)
            swipeRefreshLayout.stopRefreshing()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GithubRepositoryAdapter(this)
        recyclerView.adapter = adapter

        //On item click get repository url and open it in browser
        adapter.onItemClick = { viewRepositoryUrl ->
            openBrowser(viewRepositoryUrl)
        }
    }

    /**
     * Opens specified url in browse r
     * */

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    /**
     * Observe liveData changes and update UI regardless to [ScreenState]
     * */

    private fun setupObservers() {

        mViewModel.fetchGithubRepositories().observe(this, githubReposObserver)

        mViewModel.screenState.observe(this, Observer { state ->
            when (state) {
                is ScreenState.Loading -> {
                    hideNoDataView()
                    hideNoInternetView()
                    showProgressBar()
                }
                is ScreenState.EmptyResult -> {
                    hideProgressBar()
                    hideNoInternetView()
                    showNoDataView()
                }
                is ScreenState.InternetError -> {
                    hideNoDataView()
                    hideRecyclerView()
                    hideProgressBar()
                    showSnackBar(getString(R.string.message_internet_error))
                    showNoInternetView()
                }
                is ScreenState.Success -> {
                    showRecyclerView()
                    hideNoDataView()
                    hideNoInternetView()
                    hideProgressBar()
                }
                is ScreenState.Error -> {
                    hideRecyclerView()
                    hideNoInternetView()
                    hideProgressBar()
                    showNoDataView()
                    showSnackBar(message = state.message ?: getString(state.resId))
                }
            }
        })
    }

    private fun showRecyclerView() {
        recyclerView.makeVisible()
    }

    private fun hideRecyclerView() {
        recyclerView.makeGone()
    }

    /**
     * Display layout to inform user that there are no repositories from server
     * */

    private fun showNoDataView() {
        noDataView.makeVisible()
    }

    private fun hideNoDataView() {
        noDataView.makeGone()
    }

    /**
     * Display layout to inform user that there is no internet connection
     * */

    private fun showNoInternetView() {
        noInternetView.makeVisible()
    }

    private fun hideNoInternetView() {
        noInternetView.makeGone()
    }

    override fun onDestroy() {
        super.onDestroy()
        //release resources
        Injector.instance.releaseActivityComponent()
        NetworkUtils.instance.unregisterNetworkChangeCallback()
    }
}
