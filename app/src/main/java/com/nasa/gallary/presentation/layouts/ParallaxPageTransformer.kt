package com.nasa.gallary.presentation.layouts

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.nasa.gallary.R

class ParallaxPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        if (position < -1) {
            page.alpha = 1f
        } else if (position <= 1) {
            page.findViewById<View>(R.id.ivBackground).translationX = -position * (pageWidth / 2)
        } else {
            page.alpha = 1f
        }
    }
}