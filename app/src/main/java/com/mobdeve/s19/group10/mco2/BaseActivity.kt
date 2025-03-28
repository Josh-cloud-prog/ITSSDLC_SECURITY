package com.mobdeve.s19.group10.mco2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drawer)

        drawerLayout = findViewById(R.id.drawer_layout)

        // Set up the navigation view
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_nearby_places -> {
                    startActivity(Intent(this, NearbyPlacesActivity::class.java))
                    true
                }
                R.id.nav_attraction -> {
                    startActivity(Intent(this, AttractionActivity::class.java)) // Link to AttractionActivity
                    true
                }
                R.id.nav_restaurant -> {
                    startActivity(Intent(this, RestaurantActivity::class.java)) // Link to RestaurantActivity
                    true
                }
                R.id.nav_accommodation -> {
                    startActivity(Intent(this, AccommodationActivity::class.java)) // Link to AccommodationActivity
                    true
                }
                else -> false
            }
        }
    }
}
