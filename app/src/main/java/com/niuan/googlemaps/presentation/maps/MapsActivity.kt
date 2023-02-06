package com.niuan.googlemaps.presentation.maps

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.niuan.googlemaps.R
import com.niuan.googlemaps.databinding.ActivityMapsBinding
import com.niuan.googlemaps.core.helper.LocationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel : MapsViewModel by viewModel()
    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding
    private var polygonAux : ArrayList<LatLng> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.responseGetPolygons.observe(this){ polygons ->
            addPolygons(polygons)
        }

        viewModel.responseSavePolygons.observe(this){result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListeners(){

        binding.drawPolygon.setOnClickListener {
            viewModel.latLngList.add(polygonAux)
            mMap?.addPolygon(PolygonOptions().addAll(polygonAux).fillColor(Color.RED))
            polygonAux = arrayListOf()
        }

        binding.deleteScreen.setOnClickListener {
            mMap?.clear()
        }
        binding.deleteMemory.setOnClickListener {
            viewModel.clearMemory()
            mMap?.clear()
        }

        binding.savePolygon.setOnClickListener {
            viewModel.savePolygons()
        }

        mMap?.setOnMapClickListener {
            polygonAux.add(it)
            mMap?.addMarker(MarkerOptions().position(it))
        }

        binding.loadPolygons.setOnClickListener {
            viewModel.getPolygons()
        }
    }

    override fun onResume() {
        super.onResume()
        if(LocationHelper.isLocationEnabled(this).not()) {
            return
        }
        requestPermission()
    }

    private fun addPolygons(polygons : ArrayList<ArrayList<LatLng>>){

        if (polygons.isEmpty()) {
            Toast.makeText(this, "Voce não possui dados salvos", Toast.LENGTH_SHORT).show()
            return
        }

        polygons.forEach {
            val polygonOptions = PolygonOptions()
            polygonOptions.addAll(it)
            polygonOptions.fillColor(Color.RED)
            mMap?.addPolygon(polygonOptions)
        }
    }

    private fun initMap() {
        if(mMap == null) {
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.isMyLocationEnabled = true
        mMap?.uiSettings?.isMyLocationButtonEnabled = true

        initListeners()
    }

    private fun requestPermission(){

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if(report.areAllPermissionsGranted()){
                            initMap()
                        }
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(this@MapsActivity,it.name, Toast.LENGTH_SHORT).show()
            }
            .check()
    }

}
