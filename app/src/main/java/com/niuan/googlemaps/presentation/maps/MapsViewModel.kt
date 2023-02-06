package com.niuan.googlemaps.presentation.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.niuan.googlemaps.domain.usecases.GetPolygons
import com.niuan.googlemaps.domain.usecases.SavePolygons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(private val getPolygonsUseCase: GetPolygons, private val savePolygonsUseCase: SavePolygons) : ViewModel() {

    var latLngList = ArrayList<ArrayList<LatLng>>()
    val responseGetPolygons = MutableLiveData<ArrayList<ArrayList<LatLng>>>()
    val responseSavePolygons = MutableLiveData<String>()

    fun getPolygons(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = getPolygonsUseCase()
                responseGetPolygons.postValue(result)
            }
        }
        catch (t : Throwable){
            responseGetPolygons.postValue(arrayListOf())
        }
    }

    fun savePolygons(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = savePolygonsUseCase(latLngList)
                responseSavePolygons.postValue("Salvo")
            }
        }
        catch (t : Throwable){
            responseSavePolygons.postValue(t.message?: "Erro")
        }
    }
}