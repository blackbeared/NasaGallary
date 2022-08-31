package com.nasa.gallary.presentation.viewmodel

import NasaData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.gallary.app.common.Status
import com.nasa.gallary.app.common.doOnStatusChanged
import com.nasa.gallary.app.common.doOnSuccess
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import kotlinx.coroutines.flow.launchIn

class HomeViewModel(private val getNasaDataUseCase: GetNasaDataUseCase) : ViewModel() {

    val nasaDataContents = MutableLiveData<List<NasaData>>()

    private val ownerStatus = MutableLiveData<Status>()

    fun getNasaData(forceNetwork: Boolean)  {
        getNasaDataUseCase.getNasaData(forceNetwork).doOnSuccess { data ->
            nasaDataContents.value = data
        }.doOnStatusChanged {
            ownerStatus.value = it
        }.launchIn(viewModelScope)
    }
}