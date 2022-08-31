package com.nasa.gallary.data.data_sources.local

import NasaData
import com.nasa.gallary.data.data_sources.NasaDatasource

class LocalNasaDatasource : NasaDatasource {

    override suspend fun getNasaData(): List<NasaData> {
        return emptyList()
    }
}