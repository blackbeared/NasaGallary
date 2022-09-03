package com.nasa.gallary.domain.repository

import NasaData

interface NasaRepository {

    suspend fun getNasaData(forceNetwork: Boolean): List<NasaData>?

}