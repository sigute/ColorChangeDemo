package com.github.sigute.colorchangedemo

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withBackgroundColor(color: Int): Matcher<View> {
    return object : BoundedMatcher<View, View>(View::class.java) {
        override fun matchesSafely(view: View): Boolean {
            return color == (view.background as ColorDrawable).color
        }

        override fun describeTo(description: Description) {
            description.appendText("with text color: ")
        }
    }
}
