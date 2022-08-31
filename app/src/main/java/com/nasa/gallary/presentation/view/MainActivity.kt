package com.nasa.gallary.presentation.view

import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun init() {
        setSupportActionBar(binding.toolbar)

    }
}