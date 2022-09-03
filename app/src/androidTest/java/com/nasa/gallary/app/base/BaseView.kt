package com.nasa.gallary.app.base

import android.os.SystemClock
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher

const val DEFAULT_TIMEOUT: Int = 30000
const val CHECK_INTERVAL: Long = 500

open class BaseView {
    // waiting mechanism modified from SO, https://stackoverflow.com/a/56499223
    // not adopt robot pattern, only take the code
    companion object {
        fun searchFor(matcher: Matcher<View>): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun getDescription(): String {
                    return "searching for view: $matcher"
                }

                override fun perform(uiController: UiController?, view: View?) {
                    var tries = 0
                    val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)

                    childViews.forEach {
                        tries++
                        if (matcher.matches(it)) {
                            return
                        }
                    }

                    throw NoMatchingViewException.Builder().withRootView(view).withViewMatcher(
                        matcher
                    ).build()
                }
            }
        }
    }

    fun waitFor(
        viewMatcher: Matcher<View>,
        timeout: Int = DEFAULT_TIMEOUT,
        interval: Long = CHECK_INTERVAL
    ): ViewInteraction {
        val maxTries = timeout / interval.toInt()
        var tries = 0

        for (i in 0..maxTries) {
            try {
                tries++
                onView(isRoot()).perform(searchFor(viewMatcher))
                return onView(viewMatcher)
            } catch (e: Exception) {
                if (tries == maxTries) {
                    throw e
                }
                SystemClock.sleep(interval)
            }
        }

        throw Exception("Error finding a view matching $viewMatcher")
    }

    fun click(@IdRes id: Int) {
        waitFor(withId(id)).perform(click())
    }

    fun type(@IdRes id: Int, text: String) {
        click(id)

        // TODO closeSoftKeyboard() for every input?
        onView(withId(id)).perform(typeText(text), closeSoftKeyboard())
    }

    fun swipeUp(speed: Swiper = Swipe.FAST): GeneralSwipeAction = GeneralSwipeAction(
        speed, GeneralLocation.BOTTOM_CENTER,
        GeneralLocation.TOP_CENTER, Press.FINGER
    )

    fun swipeLeft(speed: Swiper = Swipe.FAST, press: Press = Press.FINGER): GeneralSwipeAction =
        GeneralSwipeAction(
            speed, GeneralLocation.CENTER_RIGHT,
            GeneralLocation.CENTER_LEFT,
            press
        )
}