package com.example.wisataku.ui.map

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.wisataku.R
import com.example.wisataku.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.appbar.MaterialToolbar

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapBinding

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let { updateLocation(it) }
        }
    }

    private var selectedMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentMap = supportFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment
        val toolbar = findViewById<MaterialToolbar>(R.id.mtMap)
        setSupportActionBar(toolbar)

        val mapSearchView = findViewById<SearchView>(R.id.svMap)

        mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    Log.d("Search", "Query submitted: $it")
                    searchLocation(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        fragmentMap.getMapAsync(this)

        binding.ivBack.setOnClickListener {
            finish()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { updateLocation(it) }
            }
        }

        Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun updateLocation(location: Location) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(currentLatLng)
                .title("My Location")
                .snippet("Lat: ${location.latitude}, Long: ${location.longitude}")
        )
    }

    private fun searchLocation(location: String) {
        val geocoder = Geocoder(this)
        var addressList: List<Address>? = null

        try {
            addressList = geocoder.getFromLocationName(location, 5)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Search", "Error while searching for location: ${e.message}")
        }

        Log.d("Search", "Searching for location: $location")

        if (!addressList.isNullOrEmpty()) {
            googleMap.clear()

            for (address in addressList) {
                val latLng = LatLng(address.latitude, address.longitude)
                googleMap.addMarker(MarkerOptions().position(latLng).title(address.featureName))
            }

            val builder = LatLngBounds.Builder()

            for (address in addressList) {
                builder.include(LatLng(address.latitude, address.longitude))
            }

            val bounds = builder.build()
            val padding = 100
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.animateCamera(cu)

            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)
            googleMap.addMarker(MarkerOptions().position(latLng).title(location))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        } else {
            Log.d("Search", "Location not found: $location")
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true

        googleMap.setOnMapLongClickListener { latLng ->
            val newMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("New Marker")
                    .snippet("Lat: ${latLng.latitude} Long: ${latLng.longitude}")
                    .icon(vectorToBitmap(R.drawable.baseline_android_24, Color.parseColor("#3DDC84")))
            )
            selectedMarker = newMarker
        }

        googleMap.setOnPoiClickListener { pointOfInterest ->
            val poiMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(pointOfInterest.latLng)
                    .title(pointOfInterest.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
            )
            poiMarker?.showInfoWindow()
        }

        getMyLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_map, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }

            R.id.satellite_type -> {
                googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }

            R.id.terrain_type -> {
                googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }

            R.id.hybrid_type -> {
                googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun vectorToBitmap(
        @DrawableRes id: Int,
        @ColorInt color: Int
    ): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)

        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found for id: $id\"")
            return BitmapDescriptorFactory.defaultMarker()
        }

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)

        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            getMyLocation()
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener(this, OnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                })
        }

        else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}