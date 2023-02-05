package com.niuan.googlemaps.data.repositories

import com.google.android.gms.maps.model.LatLng
import com.niuan.googlemaps.data.data_source.LocalDataSource
import com.niuan.googlemaps.data.mappers.PolygonsToStringMapper
import com.niuan.googlemaps.data.mappers.StringToPolygonsMapper
import com.niuan.googlemaps.domain.repositories.Repository

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val polygonsToStringMapper: PolygonsToStringMapper,
    private val stringToPolygonsMapper: StringToPolygonsMapper
) : Repository {

    override suspend fun savePolygons(polygons: ArrayList<ArrayList<LatLng>>) {
        val jsonString = polygonsToStringMapper.map(polygons)
        localDataSource.addPolygon(jsonString)
    }

    override suspend fun getPolygon(): ArrayList<ArrayList<LatLng>> {
        val jsonString = localDataSource.getPolygon()
        val polygons = stringToPolygonsMapper.map(jsonString)
        return polygons
    }
}