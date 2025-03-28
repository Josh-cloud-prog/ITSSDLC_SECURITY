package com.mobdeve.s19.group10.mco2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.SupportMapFragment
import kotlin.math.*

class NearbyPlacesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mMapFragment: SupportMapFragment
    private lateinit var touristAdapter: TouristAdapter
    private lateinit var touristList: MutableList<TouristAttraction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nearbyplaces)


        mMapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mMapFragment.getMapAsync(this)


        val recyclerView: RecyclerView = findViewById(R.id.rv_nearbyplaces)
        recyclerView.layoutManager = LinearLayoutManager(this)


        touristList = getTouristAttractions()


        touristAdapter = TouristAdapter(touristList, object : TouristAdapter.OnItemClickListener {
            override fun onItemClick(touristAttraction: TouristAttraction) {

                val intent = Intent(this@NearbyPlacesActivity, PlaceDetailsActivity::class.java)


                intent.putExtra("TOURIST_NAME", touristAttraction.name)
                intent.putExtra("TOURIST_LATITUDE", touristAttraction.latitude)
                intent.putExtra("TOURIST_LONGITUDE", touristAttraction.longitude)

                startActivity(intent)
            }
        })



        recyclerView.adapter = touristAdapter


        checkLocationPermission()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap


        val dlsuManila = LatLng(14.564904516188415, 120.99314822878337)


        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dlsuManila, 15f))


        mGoogleMap.addMarker(
            MarkerOptions().position(dlsuManila).title("De La Salle University Manila")
        )


        touristList.forEach { attraction ->
            attraction.distance = calculateDistance(
                dlsuManila.latitude, dlsuManila.longitude,
                attraction.latitude, attraction.longitude
            )
        }


        touristList.sortBy { attraction ->

            val distanceString = attraction.distance?.replace(" km", "")?.trim() ?: ""
            val distanceValue = try {
                distanceString.toDouble()
            } catch (e: NumberFormatException) {
                Double.MAX_VALUE
            }
            distanceValue
        }


        touristAdapter.notifyDataSetChanged()


        touristList.forEach { attraction ->
            val attractionLatLng = LatLng(attraction.latitude, attraction.longitude)
            mGoogleMap.addMarker(
                MarkerOptions().position(attractionLatLng).title(attraction.name)
            )
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {

        val radius = 6371
        val latDiff = Math.toRadians(lat2 - lat1)
        val lonDiff = Math.toRadians(lon2 - lon1)

        val a = sin(latDiff / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(lonDiff / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = radius * c

        return String.format("%.2f km", distance)
    }


    private fun getTouristAttractions(): MutableList<TouristAttraction> {
        return mutableListOf(
            TouristAttraction("Rizal Park", 14.5796, 120.9795),
            TouristAttraction("National Museum", 14.5811, 120.9789),
            TouristAttraction("Manila Ocean Park", 14.5877, 120.9770),
            TouristAttraction("Intramuros", 14.5860, 120.9773),
            TouristAttraction("Fort Santiago", 14.5957, 120.9783),
            TouristAttraction("San Agustin Church", 14.5894, 120.9763)
        )
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}