package com.niuan.googlemaps.core.utils

import com.google.gson.Gson
import com.niuan.googlemaps.core.helper.SharedPreferenceHelper
import com.niuan.googlemaps.data.data_source.LocalDataSource
import com.niuan.googlemaps.data.data_source.LocalDataSourceImpl
import com.niuan.googlemaps.data.mappers.PolygonsToStringMapper
import com.niuan.googlemaps.data.mappers.StringToPolygonsMapper
import com.niuan.googlemaps.data.repositories.RepositoryImpl
import com.niuan.googlemaps.domain.repositories.Repository
import com.niuan.googlemaps.domain.usecases.ClearMemory
import com.niuan.googlemaps.domain.usecases.GetPolygons
import com.niuan.googlemaps.domain.usecases.SavePolygons
import com.niuan.googlemaps.presentation.maps.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    single {Gson()}
    single { SharedPreferenceHelper(get()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single { PolygonsToStringMapper(get()) }
    single { StringToPolygonsMapper(get()) }
    single { GetPolygons(get()) }
    single { SavePolygons(get()) }
    single { ClearMemory(get()) }
    single<Repository> { RepositoryImpl(get(), get(), get()) }
    viewModel {MapsViewModel(get(), get(), get())}
}