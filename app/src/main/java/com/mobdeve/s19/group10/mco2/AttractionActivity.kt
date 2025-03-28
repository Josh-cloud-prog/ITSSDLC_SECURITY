package com.mobdeve.s19.group10.mco2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AttractionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attraction_page)

        val attractionList = listOf(
            Attraction("Manila Ocean Park", "Manila, Philippines", R.drawable.attraction_beach),
            Attraction("Luneta Park", "Manila, Philippines", R.drawable.attraction_beach),
            Attraction("Intramuros", "Manila, Philippines", R.drawable.attraction_beach)
        )

        val attractionRecyclerView = findViewById<RecyclerView>(R.id.attractionRecyclerView)
        attractionRecyclerView.layoutManager = LinearLayoutManager(this)
        attractionRecyclerView.adapter = AttractionAdapter(attractionList)
    }
}
