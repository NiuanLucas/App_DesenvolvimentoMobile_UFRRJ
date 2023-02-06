package com.niuan.googlemaps.domain.repositories

import com.google.android.gms.maps.model.LatLng

interface Repository {
    suspend fun savePolygons(polygons : ArrayList<ArrayList<LatLng>>)
    suspend fun getPolygon() : ArrayList<ArrayList<LatLng>>
    suspend fun clearMemory()
}