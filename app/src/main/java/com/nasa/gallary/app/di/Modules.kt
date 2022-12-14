package com.nasa.gallary.app.di

import com.nasa.gallary.app.di.NetworkModule.provideGson
import com.nasa.gallary.app.di.NetworkModule.provideNasaApiService
import com.nasa.gallary.app.di.NetworkModule.provideOfflineInterceptor
import com.nasa.gallary.app.di.NetworkModule.provideOkHTTPClient
import com.nasa.gallary.app.di.NetworkModule.provideOnlineInterceptor
import com.nasa.gallary.app.di.NetworkModule.provideRetrofit
import com.nasa.gallary.data.data_sources.local.LocalNasaDatasource
import com.nasa.gallary.data.data_sources.remote.RemoteNasaDatasource
import com.nasa.gallary.data.repository.NasaRepositoryImpl
import com.nasa.gallary.domain.repository.NasaRepository
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import com.nasa.gallary.presentation.details.viewmodel.DetailViewModel
import com.nasa.gallary.presentation.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Modules {

    val dataModel = module {
        single { provideGson() }
        single(named("OfflineInterceptor")) { provideOfflineInterceptor() }
        single(named("OnlineInterceptor")) { provideOnlineInterceptor() }
        single { provideOkHTTPClient(get(), get(named("OfflineInterceptor")), get(named("OnlineInterceptor"))) }
        single { provideRetrofit(get(), get()) }
        single { provideNasaApiService(get()) }
    }

    val dataSource = module {
        single { RemoteNasaDatasource(get()) }
        single { LocalNasaDatasource(get()) }
    }

    val domainModel = module {
        single<NasaRepository> { NasaRepositoryImpl(get(), get()) }
    }

    val useCaseModel = module {
        single { GetNasaDataUseCase(get()) }
    }

    val viewModel = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailViewModel(get()) }
    }
}