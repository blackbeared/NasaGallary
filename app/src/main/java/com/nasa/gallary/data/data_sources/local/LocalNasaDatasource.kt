package com.nasa.gallary.data.data_sources.local

import NasaData
import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nasa.gallary.data.data_sources.NasaDatasource
import org.koin.java.KoinJavaComponent.get
import java.io.IOException


class LocalNasaDatasource(private val context: Application) : NasaDatasource {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getNasaData(forceNetwork: Boolean): List<NasaData>? {
        lateinit var jsonString: String
        return try {
            jsonString = context.assets.open("nasa_data.json")
                .bufferedReader()
                .use { it.readText() }
            val listCountryType = object : TypeToken<List<NasaData>>() {}.type
            get<Gson>(Gson::class.java).fromJson(jsonString, listCountryType)
        } catch (ioException: IOException) {
            throw Exception("No Data Found")
        }
    }
}