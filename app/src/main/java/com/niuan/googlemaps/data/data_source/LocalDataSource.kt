package com.niuan.googlemaps.data.data_source


interface LocalDataSource {

    suspend fun addPolygon(json : String)
    suspend fun getPolygon() :String
    suspend fun clearMemory()
}