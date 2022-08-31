package com.nasa.gallary.presentation.view

import android.util.Log
import com.google.gson.Gson
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.databinding.ActivityMainBinding
import com.nasa.gallary.presentation.viewmodel.HomeViewModel
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeViewModel : HomeViewModel by inject(HomeViewModel::class.java)

    override fun init() {
        setSupportActionBar(binding.toolbar)
        homeViewModel.getNasaData(false)
        homeViewModel.nasaDataContents.observe(this) { value ->
            Log.d("DATA_FOUND", get<Gson>(Gson::class.java).toJson(value))
        }
    }
}