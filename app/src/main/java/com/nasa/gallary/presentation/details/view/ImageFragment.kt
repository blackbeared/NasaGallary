package com.nasa.gallary.presentation.details.view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseFragment
import com.nasa.gallary.app.constants.ArgConstants.NASA_DATA
import com.nasa.gallary.app.constants.OtherConstants.ABROAD
import com.nasa.gallary.app.constants.OtherConstants.SWITCHED
import com.nasa.gallary.app.constants.OtherConstants.TO_COLLAPSED_STATE
import com.nasa.gallary.app.constants.OtherConstants.TO_EXPANDED_STATE
import com.nasa.gallary.app.constants.OtherConstants.WAIT_FOR_SWITCH
import com.nasa.gallary.app.extensions.*
import com.nasa.gallary.databinding.FragmentImageBinding
import com.nasa.gallary.presentation.home.item_model.NasaModel
import org.koin.java.KoinJavaComponent.get
import kotlin.math.abs

class ImageFragment : BaseFragment<FragmentImageBinding>(R.layout.fragment_image){

    lateinit var nasaModel: NasaModel
    private val imageLoadedListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            activity?.let { ActivityCompat.startPostponedEnterTransition(it) }
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            activity?.let { ActivityCompat.startPostponedEnterTransition(it) }
            binding.ivBackground.animate().scaleXBy(0.1f).scaleYBy(0.1f).setDuration(5000L).start()
            resource.toBitmap().getDominantColor().let { color ->
                binding.collapsingToolbar.let {
                    it.setBackgroundColor(color)
                    it.setStatusBarScrimColor(color)
                    it.setContentScrimColor(color)
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nasaModel = get<Gson>(Gson::class.java).fromJson(
                it.getString(NASA_DATA),
                NasaModel::class.java
            )
        }
    }

    fun getCoverImage(): View {
        return binding.ivBackground
    }

    override fun init() {
        binding.listener = imageLoadedListener
        binding.nasaModel = nasaModel

        binding.toolbarMain.navigationIcon?.setTint(Color.WHITE)
        binding.toolbarMain.setNavigationOnClickListener {
            activity?.finishAfterTransition()
        }
        binding.appbar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (nasaModel.isCalculated.not()) {
                nasaModel.isCalculated = true
            }
            val offset = abs(verticalOffset / appBarLayout.totalScrollRange.toFloat())
            updateViews(offset)
        })
    }

    private fun updateViews(percentOffset: Float) {
        nasaModel.getPairResult(percentOffset).apply {
            when {
                nasaModel.cashCollapseState != null && nasaModel.cashCollapseState != this -> {
                    when (first) {
                        TO_COLLAPSED_STATE -> {
                            binding.title.slideOutHideAnim(2)
                            binding.copyright.slideOutHideAnim(1)
                            binding.ivDownArrow.slideOutHideAnim(0)
                        }

                        TO_EXPANDED_STATE -> {
                            binding.title.slideInShowAnim(0)
                            binding.copyright.slideInShowAnim(1)
                            binding.ivDownArrow.slideInShowAnim(2)
                        }
                    }
                    nasaModel.cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    nasaModel.cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }
        }
    }

    fun closeShutter() {
        binding.appbar.setExpanded(true, true)
    }

    companion object {
        fun newInstance(param1: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(NASA_DATA, param1)
                }
            }
    }
}