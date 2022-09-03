package com.nasa.gallary.presentation.home.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.gallary.app.base.BaseViewModel
import com.nasa.gallary.app.common.doOnStatusChanged
import com.nasa.gallary.app.common.doOnSuccess
import com.nasa.gallary.app.utils.NetworkState
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import com.nasa.gallary.presentation.home.item_model.NasaModel
import com.nasa.gallary.presentation.home.item_model.toNasaModel
import kotlinx.coroutines.flow.launchIn

class HomeViewModel(private val getNasaDataUseCase: GetNasaDataUseCase) : BaseViewModel() {

    val nasaDataContents = MutableLiveData<List<NasaModel>>()
    val ownerStatus = ObservableField(NetworkState.LOADING)
    val errorString = ObservableField("")

    fun initData(){
        loadNasaData(true).launchIn(viewModelScope)
    }

    fun initTestingData(): LiveData<List<NasaModel>> {
        loadNasaData(true)
        return nasaDataContents
    }

    fun loadNasaData(forceNetwork: Boolean) = getNasaDataUseCase.getNasaData(forceNetwork)
            .doOnSuccess { data ->
                nasaDataContents.postValue(data.map { it.toNasaModel() }.sortedByDescending { it.timeMillis })
            }.doOnStatusChanged { status , error ->
                ownerStatus.set(status)
                errorString.set(error)
            }
}