package com.nasa.gallary.data.data_sources.remote

import NasaData

interface NasaApiService {

    suspend fun getNasaData(): List<NasaData>
}