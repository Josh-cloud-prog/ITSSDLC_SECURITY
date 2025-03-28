package com.mobdeve.s19.group10.mco2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_page)

        val restaurantList = listOf(
            Restaurant("Cafe Juanita", "4.5", "Manila, Philippines", R.drawable.kubli_bistro),
            Restaurant("Melo's", "4.7", "Manila, Philippines", R.drawable.kubli_bistro),
            Restaurant("Locavore", "4.3", "Manila, Philippines", R.drawable.kubli_bistro)
        )

        val restaurantRecyclerView = findViewById<RecyclerView>(R.id.restaurantRecyclerView)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = RestaurantAdapter(restaurantList)
    }
}
