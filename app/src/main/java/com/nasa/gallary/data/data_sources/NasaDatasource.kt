package com.nasa.gallary.data.data_sources

import NasaData

interface NasaDatasource {

    suspend fun getNasaData(forceNetwork: Boolean): List<NasaData>?

}