package com.nasa.gallary.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.nasa.gallary.presentation.home.item_model.NasaModel

class BaseAdapter<T : BaseModel>(
    lifecycleOwner: LifecycleOwner,
    private val items: MutableLiveData<List<T>>,
    private val callback: (GenericVH, T, position: Int) -> Unit,
) : RecyclerView.Adapter<BaseAdapter.GenericVH>() {

    init {
        items.observe(lifecycleOwner) {
            // Redesign adapter to support Pagination and
            // DiffUtils Callback so that only items that are being changed can be updated
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericVH {
        return GenericVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.value?.size?:0

    override fun getItemViewType(position: Int) = items.value?.get(position)?.resId?:0

    override fun onBindViewHolder(holder: GenericVH, position: Int) {
        items.value?.get(position)?.let {
            callback.invoke(holder, it, position)
        }
    }

    class GenericVH(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}