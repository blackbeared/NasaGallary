package com.nasa.gallary.data.data_sources.remote

import NasaData
import retrofit2.http.GET


interface NasaApiService {

    @GET("reference/url/here")
    suspend fun getNasaData(): List<NasaData>
}