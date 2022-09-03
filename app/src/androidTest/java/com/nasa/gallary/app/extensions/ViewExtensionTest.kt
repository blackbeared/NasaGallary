package com.nasa.gallary.app.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewExtensionTest {

    private lateinit var bitmap: Bitmap

    @Before
    fun setup() {
        bitmap = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.RED)
    }

    @Test
    fun testCutoffTransformationNormalCase() {
        Assert.assertEquals(bitmap.getDominantColor() , Color.RED)
    }
}