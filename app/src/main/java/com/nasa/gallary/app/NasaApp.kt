package com.nasa.gallary.app

import android.app.Application
import android.net.Network
import com.nasa.gallary.app.di.Modules
import com.nasa.gallary.app.utils.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NasaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkUtils.init(this)
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