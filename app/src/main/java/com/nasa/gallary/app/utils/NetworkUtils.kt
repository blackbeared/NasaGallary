package com.nasa.gallary.app.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    var connectivityManager: ConnectivityManager? = null

    fun init(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isNetworkAvailable(): Boolean {
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
