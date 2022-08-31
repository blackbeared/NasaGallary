package com.nasa.gallary.data.data_sources.remote

import NasaData
import com.nasa.gallary.data.data_sources.NasaDatasource

class RemoteNasaDatasource : NasaDatasource {

    override suspend fun getNasaData(): List<NasaData> {
        return emptyList()
    }
}