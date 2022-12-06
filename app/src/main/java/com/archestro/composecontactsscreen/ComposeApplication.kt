package com.archestro.composecontactsscreen

import android.app.Application
import com.archestro.composecontactsscreen.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ComposeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ComposeApplication)
            modules(viewModelModule)
        }
    }
}