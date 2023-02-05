package com.niuan.googlemaps.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringToPolygonsMapper(private val gson: Gson) : Mapper<String, ArrayList<ArrayList<LatLng>>> {
    override fun map(input: String): ArrayList<ArrayList<LatLng>> {

        val type = object : TypeToken<ArrayList<ArrayList<LatLng>>>() { }.type
        return gson.fromJson(input, type)
    }
}