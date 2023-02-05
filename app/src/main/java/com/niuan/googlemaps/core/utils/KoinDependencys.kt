package com.niuan.googlemaps.core.utils

import com.niuan.googlemaps.core.helper.SharedPreferenceHelper
import com.niuan.googlemaps.data.data_source.LocalDataSourceImpl
import com.niuan.googlemaps.data.mappers.PolygonsToStringMapper
import com.niuan.googlemaps.data.mappers.StringToPolygonsMapper
import com.niuan.googlemaps.data.repositories.RepositoryImpl
import com.niuan.googlemaps.domain.repositories.Repository
import com.niuan.googlemaps.domain.usecases.GetPolygons
import com.niuan.googlemaps.domain.usecases.SavePolygons
import org.koin.dsl.module

val modules = module {
    single { SharedPreferenceHelper(get()) }
    single { LocalDataSourceImpl(get()) }
    single { PolygonsToStringMapper(get()) }
    single { StringToPolygonsMapper(get()) }
    single { RepositoryImpl(get(), get(), get()) }
    single { GetPolygons(get()) }
    single { SavePolygons(get()) }
}