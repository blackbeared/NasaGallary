package com.nasa.gallary.presentation.home.view

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseTestActivity
import com.nasa.gallary.app.util.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get


@LargeTest
@RunWith(AndroidJUnit4::class)
internal class AppFlowTest : BaseTestActivity() {

    @Rule @JvmField
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @JvmField
    var idlingResource: IdlingResource? = null

    @Before
    fun setup(){
        idlingResource = OkHttp3IdlingResource.create("Ok!", get<OkHttpClient>(OkHttpClient::class.java))
    }

    fun waitForApiOperation(){
        while (idlingResource?.isIdleNow == false){
            Thread.sleep(1000)
        }
    }

    @Test
    fun testAppLaunchAndLoadImages(){
        val scenario = rule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        this.waitFor(ViewMatchers.withId(R.id.pbLoadNasaData))
        waitForApiOperation()
        this.waitFor(ViewMatchers.withId(R.id.rvNasaImages))
        val linearLayout = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.rvNasaImages),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        linearLayout.perform(ViewActions.click())

        val imageView2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.ivBackground),
                ViewMatchers.withContentDescription("Background Image"),
                ViewMatchers.withParent(
                    ViewMatchers.withParent(
                        Matchers.allOf(
                            ViewMatchers.withId(R.id.collapsing_toolbar),
                            ViewMatchers.withContentDescription("M33: The Triangulum Galaxy")
                        )
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        imageView2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        val appCompatImageView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.ivDownArrow),
                ViewMatchers.withContentDescription("Down Arrow"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.stuff_container),
                        childAtPosition(
                            Matchers.allOf(
                                ViewMatchers.withId(R.id.collapsing_toolbar),
                                ViewMatchers.withContentDescription("M33: The Triangulum Galaxy")
                            ),
                            2
                        )
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageView.perform(ViewActions.click())
        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withText("Date Taken"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Date Taken")))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        rule.scenario.close()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}