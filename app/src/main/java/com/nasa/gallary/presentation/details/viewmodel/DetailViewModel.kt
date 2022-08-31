package com.nasa.gallary.presentation.details.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.gallary.app.base.BaseModel
import com.nasa.gallary.app.common.Status
import com.nasa.gallary.app.common.doOnStatusChanged
import com.nasa.gallary.app.common.doOnSuccess
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import com.nasa.gallary.presentation.home.item_model.NasaModel
import com.nasa.gallary.presentation.home.item_model.toNasaModel
import kotlinx.coroutines.flow.launchIn

class DetailViewModel(private val getNasaDataUseCase: GetNasaDataUseCase) : ViewModel() {

    val nasaDataContents = MutableLiveData<List<NasaModel>>()
    val currentPage = ObservableInt(0)

    private val ownerStatus = MutableLiveData<Status>()

    fun getNasaData(forceNetwork: Boolean)  {
        getNasaDataUseCase.getNasaData(forceNetwork).doOnSuccess { data ->
            nasaDataContents.value = data.map { it.toNasaModel() }
        }.doOnStatusChanged {
            ownerStatus.value = it
        }.launchIn(viewModelScope)
    }
}