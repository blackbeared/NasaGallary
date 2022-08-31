package com.nasa.gallary.presentation.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nasa.gallary.R
import com.nasa.gallary.app.base.BaseFragment
import com.nasa.gallary.databinding.FragmentImageBinding
import com.nasa.gallary.presentation.home.item_model.NasaModel
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.get

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : BaseFragment<FragmentImageBinding>(R.layout.fragment_image) {
    private var param1: NasaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = get<Gson>(Gson::class.java).fromJson(it.getString(ARG_PARAM1), NasaModel::class.java)
        }
    }

    override fun init() {
        Glide.with(this).load(param1?.hdurl).into(binding.ivBackground)
    }

    companion object {
        fun newInstance(param1: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}