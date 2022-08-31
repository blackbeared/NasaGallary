package com.nasa.gallary.presentation.home.item_model

import NasaData
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseModel
import com.nasa.gallary.presentation.details.view.DetailActivity

class NasaModel(
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?
): BaseModel(R.layout.nasa_item){

    fun onClick(view: View){
        view.context.startActivity(Intent(view.context, DetailActivity::class.java))
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                Glide.with(view.context).load(url).into(view)
            }
        }
    }
}

fun NasaData.toNasaModel(): NasaModel {
    return NasaModel(copyright,date, explanation, hdurl, media_type, service_version, title, url)
}