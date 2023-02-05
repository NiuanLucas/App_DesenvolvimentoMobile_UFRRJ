package com.niuan.googlemaps.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.niuan.googlemaps.domain.repositories.Repository

class SavePolygons(private val repository : Repository) {
    suspend operator fun invoke(polygons : ArrayList<ArrayList<LatLng>>) = repository.savePolygons(polygons)
}