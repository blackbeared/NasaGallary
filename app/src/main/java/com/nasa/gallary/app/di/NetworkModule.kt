package com.nasa.gallary.app.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nasa.gallary.app.constants.ApiConstants
import com.nasa.gallary.app.utils.NetworkUtils
import com.nasa.gallary.data.data_sources.remote.NasaApiService
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkModule {

    fun provideNasaApiService(retrofit: Retrofit): NasaApiService =
        retrofit.create(NasaApiService::class.java)

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideOkHTTPClient(
        context: Context,
        offlineInterceptor: Interceptor,
        onlineInterceptor: Interceptor
    ): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(offlineInterceptor)
            .cache(cache)
            .build()
    }

    fun provideOnlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalResponse: Response = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                    "no-cache"
                ) ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
            ) {
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build()
            } else {
                originalResponse
            }
        }
    }

    fun provideOfflineInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            if (!NetworkUtils.isNetworkAvailable()) {
                val maxStale = 60 * 60 * 24 * 1 // Offline cache available for 1 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}