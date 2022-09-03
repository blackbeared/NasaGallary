package com.nasa.gallary.data.data_sources.remote

import NasaData
import com.nasa.gallary.data.data_sources.NasaDatasource
import retrofit2.awaitResponse

class RemoteNasaDatasource(private val nasaApiService: NasaApiService) : NasaDatasource {

    override suspend fun getNasaData(forceNetwork: Boolean): List<NasaData> {
        return nasaApiService.getNasaData()
    }
}