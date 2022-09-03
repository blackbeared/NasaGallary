package com.nasa.gallary.presentation.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.gallary.app.base.BaseViewModel
import com.nasa.gallary.app.common.doOnSuccess
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import com.nasa.gallary.presentation.home.item_model.NasaModel
import com.nasa.gallary.presentation.home.item_model.toNasaModel
import kotlinx.coroutines.flow.launchIn

class DetailViewModel(private val getNasaDataUseCase: GetNasaDataUseCase) : BaseViewModel() {

    val nasaDataContents = MutableLiveData<List<NasaModel>>()

    init {
        loadNasaData(true)
    }

    private fun loadNasaData(forceNetwork: Boolean)  {
        // No Status check or other checks required since data will always be present in cache
        getNasaDataUseCase.getNasaData(forceNetwork)
            .doOnSuccess { data ->
                nasaDataContents.value = data.map { it.toNasaModel() }.sortedByDescending { it.timeMillis }
            }.launchIn(viewModelScope)
    }
}