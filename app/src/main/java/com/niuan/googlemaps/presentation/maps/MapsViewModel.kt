package com.niuan.googlemaps.presentation.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.niuan.googlemaps.domain.usecases.ClearMemory
import com.niuan.googlemaps.domain.usecases.GetPolygons
import com.niuan.googlemaps.domain.usecases.SavePolygons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(
    private val getPolygonsUseCase: GetPolygons,
    private val savePolygonsUseCase: SavePolygons,
    private val clearMemoryUseCase: ClearMemory
) : ViewModel() {

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
                savePolygonsUseCase(latLngList)
                responseSavePolygons.postValue("Salvo")
            }
        }
        catch (t : Throwable){
            responseSavePolygons.postValue(t.message?: "Erro")
        }
    }

    fun clearMemory(){
        CoroutineScope(Dispatchers.IO).launch {
            clearMemoryUseCase()
        }
    }
}