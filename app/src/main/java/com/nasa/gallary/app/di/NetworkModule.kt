package com.nasa.gallary.app.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nasa.gallary.app.constants.ApiConstants
import com.nasa.gallary.data.data_sources.remote.NasaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    fun provideNasaApiService(retrofit: Retrofit): NasaApiService = retrofit.create(NasaApiService::class.java)

    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

}