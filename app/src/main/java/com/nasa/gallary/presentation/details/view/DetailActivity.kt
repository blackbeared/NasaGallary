package com.nasa.gallary.presentation.details.view

import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.SharedElementCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseActivity
import com.nasa.gallary.app.base.ViewPagerAdapter
import com.nasa.gallary.app.constants.ArgConstants.EXTRA_INDEX
import com.nasa.gallary.databinding.ActivityDetailBinding
import com.nasa.gallary.presentation.details.viewmodel.DetailViewModel
import com.nasa.gallary.presentation.custom.ParallaxPageTransformer
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.get

// No need to implement error handling since we'll be already
// receiving cached data and no chances of API failure at moment!
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private var itemIndex = 0
    private var isReturning = false
    private val detailViewModel : DetailViewModel by viewModel()
    private val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback(){
        override fun onPageScrolled(position: Int, offset: Float,offsetPixels: Int) {
            (viewPagerAdapter.createFragment(position) as? ImageFragment)?.closeShutter()
        }
    }

    private val sharedElementCallback = object : SharedElementCallback() {
        override fun onMapSharedElements(
            names: MutableList<String?>,
            sharedElements: MutableMap<String?, View?>
        ) {
            if (isReturning) {
                val sharedElement = (viewPagerAdapter.createFragment(binding.vpNasaImages.currentItem) as? ImageFragment)?.getCoverImage()
                if (sharedElement == null) {
                    names.clear()
                    sharedElements.clear()
                } else if (itemIndex != binding.vpNasaImages.currentItem) {
                    names.clear()
                    names.add(sharedElement.transitionName)
                    sharedElements.clear()
                    sharedElements[sharedElement.transitionName] = sharedElement
                }
            }
        }
    }

    override fun init() {
        binding.detailViewModel = detailViewModel
        itemIndex = intent.getIntExtra(EXTRA_INDEX, 0)

        ActivityCompat.postponeEnterTransition(this)
        setEnterSharedElementCallback(sharedElementCallback)

        detailViewModel.nasaDataContents.observe(this) { list ->
            list.forEach {
                viewPagerAdapter.addFragment(ImageFragment.newInstance(get<Gson>(Gson::class.java).toJson(it)))
            }
            viewPagerAdapter.notifyDataSetChanged()
            binding.vpNasaImages.setCurrentItem(itemIndex, false)
        }
        binding.vpNasaImages.apply {
            adapter = viewPagerAdapter
            setPageTransformer(ParallaxPageTransformer())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.vpNasaImages.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onStop() {
        binding.vpNasaImages.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onStop()
    }

    override fun finishAfterTransition() {
        isReturning = true
        val data = Intent()
        data.putExtra(EXTRA_INDEX, binding.vpNasaImages.currentItem)
        setResult(RESULT_OK, data)
        super.finishAfterTransition()
    }
}