package com.niuan.googlemaps.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.niuan.googlemaps.helper.LocationHelper


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var latLngList = ArrayList<LatLng>()
    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListeners(){
        binding.buttonClear.setOnClickListener {
            mMap?.clear()
            latLngList = arrayListOf()
        }

        mMap?.setOnMapClickListener {
            latLngList.add(it)
            mMap?.addMarker(MarkerOptions().position(it))
        }

        binding.buttonDraw.setOnClickListener {
            if (latLngList.isEmpty()) {
                Toast.makeText(this, "Adicione marcadores para poder desenhar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mMap?.addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .addAll(latLngList)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if(LocationHelper.isLocationEnabled(this).not()) {
            return
        }
        requestPermission()
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
        mMap?.isMyLocationEnabled = true;
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
