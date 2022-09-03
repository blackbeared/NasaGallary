package com.nasa.gallary.data.data_sources.remote

import NasaData
import com.nasa.gallary.app.common.Resource
import com.nasa.gallary.app.constants.ApiConstants.GALLARY_DATA
import retrofit2.Call
import retrofit2.http.GET


interface NasaApiService {

    @GET(GALLARY_DATA)
    suspend fun getNasaData(): List<NasaData>
}