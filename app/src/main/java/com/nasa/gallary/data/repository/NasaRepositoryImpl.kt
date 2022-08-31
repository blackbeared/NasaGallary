package com.nasa.gallary.data.repository

import NasaData
import com.nasa.gallary.data.data_sources.local.LocalNasaDatasource
import com.nasa.gallary.data.data_sources.remote.RemoteNasaDatasource
import com.nasa.gallary.domain.repository.NasaRepository

class NasaRepositoryImpl(private val localNasaDatasource: LocalNasaDatasource, private val remoteNasaDatasource: RemoteNasaDatasource) : NasaRepository{

    override suspend fun getNasaData(forceNetwork: Boolean): List<NasaData> {
        if(!forceNetwork)
            return localNasaDatasource.getNasaData()
        return remoteNasaDatasource.getNasaData()
    }
}