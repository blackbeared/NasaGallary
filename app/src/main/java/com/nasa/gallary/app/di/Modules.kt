package com.nasa.gallary.app.di

import com.nasa.gallary.app.di.NetworkModule.provideGson
import com.nasa.gallary.app.di.NetworkModule.provideNasaApiService
import com.nasa.gallary.app.di.NetworkModule.provideRetrofit
import com.nasa.gallary.data.repository.NasaRepositoryImpl
import com.nasa.gallary.domain.repository.NasaRepository
import com.nasa.gallary.domain.use_cases.GetNasaDataUseCase
import org.koin.dsl.module

object Modules {

    val dataModel = module {
        factory { provideGson() }
        single { provideRetrofit(get()) }
        factory { provideNasaApiService(get()) }
    }

    val domainModel = module {
        single<NasaRepository> { NasaRepositoryImpl(get(), get()) }
    }

    val useCaseModel = module {
        single { GetNasaDataUseCase(get()) }
    }

    val viewModel = module {
    }
}