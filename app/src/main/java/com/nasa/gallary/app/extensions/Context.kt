package com.nasa.gallary.app.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration


fun Context.isNightModeEnabled(): Boolean {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun Context.activity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.activity()
        else -> null
    }
}