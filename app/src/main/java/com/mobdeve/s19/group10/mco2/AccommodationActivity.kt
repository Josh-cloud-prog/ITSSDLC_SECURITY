package com.mobdeve.s19.group10.mco2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AccommodationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accommodation_page)

        val accommodationList = listOf(
            Accommodation("Acme Hotel", "4.5", "Manila, Philippines", R.drawable.accommodation_baler),
            Accommodation("Sunset Resort", "4.8", "Boracay, Philippines", R.drawable.accommodation_baler),
            Accommodation("Beach House", "4.7", "Cebu, Philippines", R.drawable.accommodation_baler)
        )

        val accommodationRecyclerView = findViewById<RecyclerView>(R.id.accommodationRecyclerView)
        accommodationRecyclerView.layoutManager = LinearLayoutManager(this)
        accommodationRecyclerView.adapter = AccommodationAdapter(accommodationList)
    }
}
