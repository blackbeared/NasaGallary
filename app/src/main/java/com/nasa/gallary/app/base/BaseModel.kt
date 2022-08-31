package com.nasa.gallary.app.base

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable

open class BaseModel(@LayoutRes var resId: Int) : BaseObservable()