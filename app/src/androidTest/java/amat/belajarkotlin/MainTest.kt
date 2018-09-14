package amat.belajarkotlin

import amat.belajarkotlin.R.id.*
import amat.belajarkotlin.main.MainActivity
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class MainTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule (MainActivity::class.java)

    @Test
    fun testAppBehaviour() {

        onView(withId(navigation_favorit))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorit)).perform(click())


        onView(withId(navigation_next))
                .check(matches(isDisplayed()))
        onView(withId(navigation_next)).perform(click())

        onView(withId(listNext))
                .check(matches(isDisplayed()))
        onView(withId(listNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
        onView(withId(listNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(listNext)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        onView(withId(imageFavorite)).check(matches(isDisplayed()))
        onView(withId(imageFavorite)).perform(click())
        pressBack()

        onView(withId(navigation_favorit))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorit)).perform(click())
    }
}





