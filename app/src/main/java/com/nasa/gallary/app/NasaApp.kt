package com.nasa.gallary.app

import android.app.Application
import com.nasa.gallary.app.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NasaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NasaApp)
            modules(
                listOf(
                    Modules.dataModel,
                    Modules.dataSource,
                    Modules.domainModel,
                    Modules.useCaseModel,
                    Modules.viewModel
                )
            )
        }
    }
}