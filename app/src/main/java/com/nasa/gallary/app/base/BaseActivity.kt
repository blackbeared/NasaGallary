package com.nasa.gallary.app.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


// To be used to lazy inject global listeners or utils which
// require everywhere e.g. PrefUtils()
abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val resId: Int) : AppCompatActivity() {

    lateinit var binding: T

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = DataBindingUtil.setContentView(this@BaseActivity, resId) as T
        init()
    }
}