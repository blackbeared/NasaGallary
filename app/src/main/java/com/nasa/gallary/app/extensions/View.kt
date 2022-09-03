package com.nasa.gallary.app.extensions

import android.animation.Animator
import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun View.activity() : Activity? {
    return when (context) {
        null -> null
        is Activity -> context as? Activity
        is ContextWrapper -> (context as ContextWrapper).baseContext.activity()
        else -> null
    }
}

fun View.slideInShowAnim(position: Int){
    visibility = View.VISIBLE
    alpha = 0f
    this.translationY = this.translationY + 50
    animate().translationY(0f)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .alpha(1.0f)
        .setStartDelay((position * 50) + 100L)
        .setDuration(450)
        .setListener(null)
}

fun View.slideOutHideAnim(position: Int){
    alpha = 1f
    this.translationY = 0f
    animate().translationY(this.translationY + 50)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .alpha(0.0f)
        .setStartDelay(position * 50L)
        .setDuration(450)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        })
}