package com.mobdeve.s19.group10.mco2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var checkInButton: Button
    private lateinit var checkedInPlacesButton: Button
    private val checkedInPlaces = mutableListOf<String>() // List to store checked-in places

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location)

        // Initialize MapView
        mapView = findViewById(R.id.mv_location)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize the Check-in button
        checkInButton = findViewById(R.id.btn_checkin_location)
        checkInButton.setOnClickListener {
            checkIn()
        }

        // Initialize the Checked-in Places button
        checkedInPlacesButton = findViewById(R.id.btn_checkedin_places)
        checkedInPlacesButton.setOnClickListener {
            navigateToCheckedInPlaces()
        }
    }

    private fun navigateToCheckedInPlaces() {
        try {
            if (checkedInPlaces.isNotEmpty()) {
                val intent = Intent(this, CheckedInPlacesActivity::class.java)
                intent.putStringArrayListExtra("checkedInPlaces", ArrayList(checkedInPlaces))
                startActivity(intent)
            } else {
                Toast.makeText(this, "No places checked in yet.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("LocationActivity", "Error starting CheckedInPlacesActivity", e)
            Toast.makeText(this, "An error occurred while opening checked-in places.", Toast.LENGTH_LONG).show()
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        // Enable My Location
        mMap.isMyLocationEnabled = true

        // Display user's location on the map
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
                val userLocation = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                mMap.addMarker(MarkerOptions().position(userLocation).title("You are here"))
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIn() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0].getAddressLine(0) // Get the complete address
                    val userLocation = LatLng(location.latitude, location.longitude)

                    // Add to checked-in places
                    checkedInPlaces.add(address)

                    Toast.makeText(
                        this,
                        "Check-in Successful at: $address",
                        Toast.LENGTH_LONG
                    ).show()

                    // Optionally, add a marker for the check-in location with the address as the title
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .title("Checked in Here!")
                            .snippet(address)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                } else {
                    Toast.makeText(
                        this,
                        "Unable to get address for the location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Failed to Check-in. Location not available.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}