package com.alexpershin.githubrepos


import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.alexpershin.githubrepos.ui.activity.MainActivity
import com.alexpershin.githubrepos.ui.adapter.GithubRepositoryAdapter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private fun getItemCount(): Int {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
        return recyclerView.adapter!!.itemCount
    }

    private fun sleep(millis: Long) {
        SystemClock.sleep(millis)
    }

    private fun performRecyclerViewScrollToBottom() {
        // Scroll to end of page with position
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollToPosition<GithubRepositoryAdapter.GithubRepositoryViewHolder>(
                    getItemCount() - 1
                )
            )
    }

    private fun performRecyclerItemClick(position: Int) {
        // Perform click action on recyclerView item
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<GithubRepositoryAdapter.GithubRepositoryViewHolder>(
                    position,
                    click()
                )
            )
    }

    @Test
    fun check_item_count_test() {
        val initialPageSize = 20
        var scrollToEndCount = 0

        sleep(3000)
        performRecyclerViewScrollToBottom()
        scrollToEndCount++

        sleep(3000)
        performRecyclerViewScrollToBottom()
        scrollToEndCount++


        sleep(3000)
        performRecyclerViewScrollToBottom()
        scrollToEndCount++

        val totalFetchedItems = (scrollToEndCount * PAGE_SIZE) + initialPageSize

        assertEquals(totalFetchedItems, getItemCount())
    }

    @Test
    fun recycler_view_item_click_test() {
        sleep(3000)
        performRecyclerItemClick(getItemCount() - 1)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}

