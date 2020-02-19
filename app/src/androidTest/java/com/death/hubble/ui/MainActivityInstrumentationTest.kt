package com.death.hubble.ui

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.death.hubble.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentationTest {

    private val main = IntentsTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        Thread.sleep(2000)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.death.hubble", appContext.packageName)
    }


    /**
     * This test cases checks in the success case when the items are returned from the api and matches the item counts in
     * recycler view.
     */
    @Test
    fun onInternetResultRetrieved(){
        main.launchActivity(Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java))
        Thread.sleep(4000)
        onView(withId(R.id.images))
            .check(matches(isDisplayed()))

        onView(withId(R.id.images)).check(matches(RecyclerViewMatchers.hasItemCount(26)))

        Thread.sleep(4000)
    }

    /**
     * This test case is expected to fail in the cases when the result is returned from the api
     * and will pass in the case when the exception is raised at run time due to no internet or any other reason
     *
     */
    @Test
    fun onException(){
        main.launchActivity(Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java))
        Thread.sleep(4000)

        onView(withId(R.id.errorLayout))
            .check(matches(isDisplayed()))

        Thread.sleep(4000)
    }


    //https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
    object RecyclerViewMatchers {
        @JvmStatic
        fun hasItemCount(itemCount: Int): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(
                RecyclerView::class.java) {

                override fun describeTo(description: Description) {
                    description.appendText("has $itemCount items")
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    return view.adapter!!.itemCount == itemCount
                }
            }
        }
    }

}
