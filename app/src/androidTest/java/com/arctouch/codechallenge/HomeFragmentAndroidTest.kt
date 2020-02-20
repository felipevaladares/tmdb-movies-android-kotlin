package com.arctouch.codechallenge

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.arctouch.codechallenge.home.HomeActivity
import com.arctouch.codechallenge.home.ui.adapter.HomeMovieViewHolder
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentAndroidTest {

    @Test
    fun test_NavigationToDetails() {
        ActivityScenario.launch(HomeActivity::class.java)

        Thread.sleep(3000)//TODO

        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<HomeMovieViewHolder>(0, click())
        )

        onView(withId(R.id.homeMovieActivityParent)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

    }
}