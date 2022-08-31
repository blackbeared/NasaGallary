package com.nasa.gallary.app.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T : BaseModel>(private val items: List<T>, private val callback : (GenericVH, T)-> Unit) : RecyclerView.Adapter<BaseAdapter.GenericVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericVH {
        return GenericVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].resId

    override fun onBindViewHolder(holder: GenericVH, position: Int) = callback.invoke(holder, items[position])

    class GenericVH(private val binding : ViewDataBinding): RecyclerView.ViewHolder(binding.root)
}