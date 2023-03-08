package com.niuan.googlemaps.data.data_source


import com.niuan.googlemaps.core.helper.SharedPreferenceHelper

class LocalDataSourceImpl(private val sharedPreferenceHelper: SharedPreferenceHelper) : LocalDataSource {
    override suspend fun getPolygon() = sharedPreferenceHelper.getJson()
    override suspend fun addPolygon(json : String) = sharedPreferenceHelper.saveJson(json)
    override suspend fun clearMemory() = sharedPreferenceHelper.clearMemory()
}