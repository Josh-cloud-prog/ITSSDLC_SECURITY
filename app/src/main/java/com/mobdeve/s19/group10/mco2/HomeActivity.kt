package com.mobdeve.s19.group10.mco2

import Itinerary
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

class HomeActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var recyclerView: RecyclerView
    private lateinit var itineraryAdapter: ItineraryAdapter
    private val itineraries = mutableListOf<Itinerary>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


        mapView = findViewById(R.id.mv_home)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        recyclerView = findViewById(R.id.rv_saved_itinerary)
        itineraryAdapter = ItineraryAdapter(itineraries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itineraryAdapter
        itineraryAdapter.loadItinerariesFromFirebase()


        findViewById<ImageButton>(R.id.btn_nearby).setOnClickListener {
            startActivity(Intent(this, NearbyPlacesActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_place_details).setOnClickListener {
            startActivity(Intent(this, PlaceDetailsActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_location).setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_attraction).setOnClickListener {
            startActivity(Intent(this, AttractionActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_restaurant).setOnClickListener {
            startActivity(Intent(this, RestaurantActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_accommodation).setOnClickListener {
            startActivity(Intent(this, AccommodationActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btn_add_itinerary).setOnClickListener {
            startActivity(Intent(this, CreateItineraryActivity::class.java))
        }

        val dummyButton: ImageButton = findViewById(R.id.btn_dummy_notify)
        dummyButton.setOnClickListener {
            Log.d("HomeActivity", "Dummy button clicked!")
            sendNotification("Upcoming Trip", "Don't forget your trip to Boracay!")
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        fusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener<Location> { location ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
                val uiSettings: UiSettings = googleMap.uiSettings
                uiSettings.isScrollGesturesEnabled = false
                uiSettings.isZoomGesturesEnabled = false
                uiSettings.isRotateGesturesEnabled = false
                uiSettings.isTiltGesturesEnabled = false
                googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
            }
        })
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "channel_id_01"
        val notificationId = 101


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Default Channel"
            val channelDescription = "Channel for app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1010
                )
                return
            }
        }


        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_map)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
