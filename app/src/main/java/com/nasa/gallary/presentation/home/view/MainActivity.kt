package com.nasa.gallary.presentation.home.view

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.app.base.BaseAdapter
import com.nasa.gallary.databinding.ActivityMainBinding
import com.nasa.gallary.databinding.NasaItemBinding
import com.nasa.gallary.presentation.home.viewmodel.HomeViewModel
import org.koin.java.KoinJavaComponent.inject

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeViewModel : HomeViewModel by inject(HomeViewModel::class.java)

    override fun init() {
        binding.homeViewModel = homeViewModel
        homeViewModel.getNasaData(false)
        binding.rvNasaImages.layoutManager = GridLayoutManager(this, 3)
        binding.rvNasaImages.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.grid_item_anim)
        binding.rvNasaImages.adapter = BaseAdapter(this, homeViewModel.nasaDataContents) { vh, bm ->
            when(vh.binding){
                is NasaItemBinding -> vh.binding.nasaModel = bm
            }
        }
    }
}