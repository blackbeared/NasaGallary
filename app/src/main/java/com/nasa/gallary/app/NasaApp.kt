package com.nasa.gallary.app

import android.app.Application
import com.nasa.gallary.app.di.Modules
import org.koin.core.context.startKoin

class NasaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                listOf(
                    Modules.dataModel,
                    Modules.domainModel,
                    Modules.useCaseModel,
                    Modules.viewModel
                )
            )
        }
    }
}