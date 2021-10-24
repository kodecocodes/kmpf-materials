package com.raywenderlich.findtime.android

import android.app.Application
import com.raywenderlich.findtime.di.initializeKoin
import com.raywenderlich.findtime.di.timezoneModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class TimezoneApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())
        initializeKoin {
            androidLogger()
            androidContext(this@TimezoneApp)
            modules(timezoneModule())
        }
    }
}