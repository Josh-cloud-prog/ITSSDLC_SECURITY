package com.mobdeve.s19.group10.mco2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckedInPlacesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckedInPlacesAdapter
    private val checkedInPlacesList = mutableListOf<String>() // This will store the checked-in places

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkedinplaces)

        recyclerView = findViewById(R.id.rv_checkedinplaces)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Safely retrieve checked-in places from Intent
        val checkedInPlaces = intent.getStringArrayListExtra("checkedInPlaces") ?: ArrayList()
        checkedInPlacesList.addAll(checkedInPlaces)

        adapter = CheckedInPlacesAdapter(checkedInPlacesList)
        recyclerView.adapter = adapter

        Log.d("CheckedInPlacesActivity", "Received Places: $checkedInPlacesList")
    }


}
