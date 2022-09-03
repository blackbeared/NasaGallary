package com.nasa.gallary.app.extensions

import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

// Returns darker color
@ColorInt fun Int.darkenColor(): Int {
    return Color.HSVToColor(FloatArray(3).apply {
        Color.colorToHSV(this@darkenColor, this@apply)
        this[2] *= 0.8f
    })
}

// Returns if color is light or dark
fun Int.isLight(): Boolean {
    return ColorUtils.calculateLuminance(this) > 0.5
}

// Pallet library takes too much time to generate pallet and also fails
// sometimes, so using this small utility function to get dominant color
// Returns Color Int
fun Bitmap.getDominantColor() : Int {
    val newBitmap = Bitmap.createScaledBitmap(this, 10, 10, true)
    var color = newBitmap.getPixel(5, 5)
    if (color.isLight()) {
        color = color.darkenColor()
    }
    newBitmap.recycle()
    return color
}