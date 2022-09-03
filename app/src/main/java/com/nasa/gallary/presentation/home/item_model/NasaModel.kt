package com.nasa.gallary.presentation.home.item_model

import NasaData
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseModel
import com.nasa.gallary.app.constants.ArgConstants
import com.nasa.gallary.app.constants.OtherConstants
import com.nasa.gallary.app.utils.DateUtils
import com.nasa.gallary.presentation.details.view.DetailActivity


class NasaModel(
    val copyright: String?,
    val date: String,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String,
    val url: String?
): BaseModel(R.layout.item_nasa_data){

    internal var cashCollapseState: Pair<Int, Int>? = null
    internal var isCalculated = false
    val timeMillis: Long
        get() = DateUtils.convertStringToDate(date).time

    fun getFormattedDate(): String {
        return DateUtils.convertDateToString(date)
    }

    fun onClick(view: View, index: Int){
        val intent = Intent(view.context, DetailActivity::class.java)
        intent.putExtra(ArgConstants.EXTRA_INDEX, index)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(view.context as Activity, view, title)
        view.context.startActivity(intent, options.toBundle())
        (view.context as? Activity)?.overridePendingTransition(com.google.android.material.R.anim.abc_grow_fade_in_from_bottom, com.google.android.material.R.anim.abc_grow_fade_in_from_bottom)
    }

    fun getCopyrightText(): String {
        return "© ${copyright?:"Unknown"}"
    }

    fun getPairResult(percentOffset: Float): Pair<Int, Int> {
        return when {
            percentOffset < OtherConstants.ABROAD -> {
                Pair(OtherConstants.TO_EXPANDED_STATE, cashCollapseState?.second ?: OtherConstants.WAIT_FOR_SWITCH)
            }
            else -> {
                Pair(OtherConstants.TO_COLLAPSED_STATE, cashCollapseState?.second ?: OtherConstants.WAIT_FOR_SWITCH)
            }
        }
    }
}

fun NasaData.toNasaModel(): NasaModel {
    return NasaModel(copyright,date, explanation, hdurl, media_type, service_version, title, url)
}