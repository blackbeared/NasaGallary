package com.nasa.gallary.presentation.custom

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.nasa.gallary.R


class ParallaxPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val myOffset: Float = position * -2
        page.translationX = myOffset

        val pageWidth = page.width
        if (position < -1) {
            page.alpha = 1f
        } else if (position <= 1) {
            page.findViewById<View?>(R.id.ivBackground)?.let {
                it.translationX = -position * (pageWidth / 2)
                it.cameraDistance = 1.2f
            }
        } else {
            page.alpha = 1f
        }
    }
}