package com.nasa.gallary.app.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.nasa.gallary.R

object BindingAdapter {
    @BindingAdapter("imageThumbnail")
    @JvmStatic
    fun loadThumbnail(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.placeholderOf(R.drawable.img_placeholder))
                .into(view)
        }
    }

    @BindingAdapter("gifRes")
    @JvmStatic
    fun gifRes(view: ImageView, resource: Int) {
        Glide.with(view.context)
            .asGif()
            .load(resource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    @BindingAdapter(value = ["imageHD", "listener"], requireAll = false)
    @JvmStatic
    fun loadHD(view: ImageView, imageHD: String?, listener: RequestListener<Drawable>?) {
        if (!imageHD.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageHD)
                .listener(listener)
                .apply(RequestOptions.placeholderOf(R.drawable.img_placeholder))
                .into(view)
        }
    }
}