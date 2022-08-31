package com.nasa.gallary.presentation.details.view

import com.google.gson.Gson
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.app.base.ViewPagerAdapter
import com.nasa.gallary.databinding.ActivityDetailBinding
import com.nasa.gallary.presentation.details.viewmodel.DetailViewModel
import com.nasa.gallary.presentation.layouts.ParallaxPageTransformer
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val homeViewModel : DetailViewModel by inject(DetailViewModel::class.java)
    private val adapter = ViewPagerAdapter(supportFragmentManager)

    override fun init() {
        binding.detailViewModel = homeViewModel
        homeViewModel.getNasaData(false)
        homeViewModel.nasaDataContents.observe(this) { list ->
            list.forEach {
                adapter.addFragment(ImageFragment.newInstance(get<Gson>(Gson::class.java).toJson(it)))
            }
            adapter.notifyDataSetChanged()
        }
        binding.vpNasaImages.adapter = adapter
        binding.vpNasaImages.setPageTransformer(false, ParallaxPageTransformer())
    }
}