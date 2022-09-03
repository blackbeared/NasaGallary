package testUtils

import NasaData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nasa.gallary.presentation.home.item_model.NasaModel
import okio.buffer
import okio.source

object NasaDataReader {

    fun fakeFeedLiveModel(fileName: String): List<NasaData> {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val type = object : TypeToken<List<NasaData>>() {}.type
        return inputStream?.source()?.buffer()?.readString(Charsets.UTF_8)?.let {
            Gson().fromJson(it, type)
        } ?: emptyList()
    }

    fun fakeNasaModel(fileName: String): NasaData? {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val type = object : TypeToken<NasaData>() {}.type
        return inputStream?.source()?.buffer()?.readString(Charsets.UTF_8)?.let {
            Gson().fromJson(it, type)
        }
    }
}