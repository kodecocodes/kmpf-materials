package com.raywenderlich.findtime.di

import com.raywenderlich.findtime.TimeZoneHelper
import com.raywenderlich.findtime.TimeZoneHelperImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun timezoneModule() = module {
    single<TimeZoneHelper> { TimeZoneHelperImpl() }
}