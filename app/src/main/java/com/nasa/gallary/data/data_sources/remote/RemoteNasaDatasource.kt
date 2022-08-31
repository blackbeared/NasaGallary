package com.nasa.gallary.data.data_sources.remote

import NasaData
import com.nasa.gallary.data.data_sources.NasaDatasource

class RemoteNasaDatasource(private val nasaApiService: NasaApiService) : NasaDatasource {

    override suspend fun getNasaData(): List<NasaData> {
        return nasaApiService.getNasaData()
    }
}