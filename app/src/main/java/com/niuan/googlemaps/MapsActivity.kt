package com.niuan.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.niuan.googlemaps.databinding.ActivityMapsBinding
import java.util.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    val places = ArrayList<Place>()
    val coordList = ArrayList<LatLng>()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true;
        mMap.uiSettings.isMyLocationButtonEnabled = true

        val buttonMap = findViewById<Button>(R.id.getLocation)
        buttonMap.setOnClickListener{
            val newLocation = Place(mMap.myLocation.latitude, mMap.myLocation.longitude)
            places.add(newLocation)
            val sydney = LatLng(mMap.myLocation.latitude, mMap.myLocation.longitude)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f))

            val textMap = findViewById<TextView>(R.id.textMap)
            val placesSize = places.size
            textMap.text = "Total of Points: " + placesSize.toString()

        }

        val buttonFinish = findViewById<Button>(R.id.finish)
        buttonFinish.setOnClickListener{
            places.forEach{
                coordList.add(LatLng(it.latitude,it.longitude))
            }
            val polygonOptions = PolygonOptions()
            polygonOptions.addAll(coordList)
            polygonOptions.fillColor(Color.RED)
            polygonOptions.strokeColor(Color.BLUE)
            polygonOptions.strokeWidth(0.5f)
            mMap.addPolygon(polygonOptions)
        }

        //val sydney = LatLng(-34.0, 151.0)

    }



}

data class Place(
    val latitude: Double,
    val longitude: Double
)