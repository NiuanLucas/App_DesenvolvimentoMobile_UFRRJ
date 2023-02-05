package com.niuan.googlemaps.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.niuan.googlemaps.domain.repositories.Repository

class GetPolygons(private val repository : Repository) {
    suspend operator fun invoke() : ArrayList<ArrayList<LatLng>> = repository.getPolygon()
}