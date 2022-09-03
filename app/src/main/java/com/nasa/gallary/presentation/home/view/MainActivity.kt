package com.nasa.gallary.presentation.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.core.app.SharedElementCallback
import androidx.recyclerview.widget.GridLayoutManager
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.app.base.BaseAdapter
import com.nasa.gallary.app.constants.ArgConstants
import com.nasa.gallary.databinding.ActivityMainBinding
import com.nasa.gallary.databinding.ItemNasaDataBinding
import com.nasa.gallary.presentation.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeViewModel: HomeViewModel by viewModel()
    private var reenterBundle: Bundle? = null
    private val sharedElementCallback: SharedElementCallback = object : SharedElementCallback() {
        override fun onMapSharedElements(
            names: MutableList<String?>,
            sharedElements: MutableMap<String?, View?>
        ) {
            reenterBundle?.let {
                val currentPosition = it.getInt(ArgConstants.EXTRA_INDEX)
                val newTransitionName = homeViewModel.nasaDataContents.value?.get(currentPosition)?.title
                val newSharedElement = binding.rvNasaImages.findViewWithTag(newTransitionName) as? View
                if (newSharedElement != null) {
                    names.clear()
                    names.add(newTransitionName)
                    sharedElements.clear()
                    sharedElements[newTransitionName] = newSharedElement
                }
                reenterBundle = null
            }
        }
    }


    override fun init() {
        binding.homeViewModel = homeViewModel
        setExitSharedElementCallback(sharedElementCallback)
        homeViewModel.initData()
        binding.rvNasaImages.layoutManager = GridLayoutManager(this, 3)
        binding.rvNasaImages.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.grid_item_anim)

        // Using Normal Generic adapter since the data do not need pagination here!
        binding.rvNasaImages.adapter =
            BaseAdapter(this, homeViewModel.nasaDataContents) { vh, bm, pos ->
                when (vh.binding) {
                    is ItemNasaDataBinding -> {
                        vh.binding.nasaModel = bm
                        vh.binding.index = pos
                    }
                }
            }
    }

    override fun onActivityReenter(requestCode: Int, data: Intent) {
        super.onActivityReenter(requestCode, data)
        reenterBundle = Bundle(data.extras)
        reenterBundle?.let {
            val currentPosition= it.getInt(ArgConstants.EXTRA_INDEX)
            binding.rvNasaImages.scrollToPosition(currentPosition)
            postponeEnterTransition()
            binding.rvNasaImages.viewTreeObserver
                .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        binding.rvNasaImages.viewTreeObserver.removeOnPreDrawListener(this)
                        binding.rvNasaImages.requestLayout()
                        startPostponedEnterTransition()
                        return true
                    }
                })
        }
    }
}