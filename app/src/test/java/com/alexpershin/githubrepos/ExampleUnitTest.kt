package com.alexpershin.githubrepos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexpershin.githubrepos.repo.ApiRepository
import com.alexpershin.githubrepos.repo.DatabaseRepository
import com.alexpershin.githubrepos.ui.state.ScreenState
import com.alexpershin.githubrepos.viewmodel.MainActivityViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainActivityViewModel

    @Mock
    private lateinit var observer: Observer<ScreenState>

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var databaseRepository: DatabaseRepository

    @Before
    fun initDependencies() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainActivityViewModel(apiRepository, databaseRepository)
        viewModel.screenState.observeForever(observer)
    }

    @Test
    fun check_screen_state_test() {
        val expectedState = ScreenState.Loading

        viewModel.fetchGithubRepositories()

        val captor = ArgumentCaptor.forClass(ScreenState::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedState, value)
        }
    }

}
