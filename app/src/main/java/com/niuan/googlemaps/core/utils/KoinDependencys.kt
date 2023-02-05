package com.niuan.googlemaps.core.utils

import com.niuan.googlemaps.core.helper.SharedPreferenceHelper
import com.niuan.googlemaps.data.data_source.LocalDataSourceImpl
import org.koin.dsl.module

val modules = module {
    single { SharedPreferenceHelper(get()) }
    single { LocalDataSourceImpl(get()) }
}