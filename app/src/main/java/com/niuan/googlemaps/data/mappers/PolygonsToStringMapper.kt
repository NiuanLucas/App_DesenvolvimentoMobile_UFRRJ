package com.niuan.googlemaps.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

class PolygonsToStringMapper(private val gson: Gson) : Mapper<ArrayList<ArrayList<LatLng>>, String> {
    override fun map(input: ArrayList<ArrayList<LatLng>>): String {
        return gson.toJson(input)
    }
}