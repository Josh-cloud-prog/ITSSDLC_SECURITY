package com.mobdeve.s19.group10.mco2

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class PlaceDetailsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.placedetails)


        val touristName = intent.getStringExtra("TOURIST_NAME")


        Log.d("PlaceDetailsActivity", "Received tourist name: $touristName")


        val nameTextView: TextView = findViewById(R.id.tv_place_title)
        val detailsTextView: TextView = findViewById(R.id.tv_place_description)
        val distanceTextView: TextView = findViewById(R.id.tv_distance)
        val imageView: ImageView = findViewById(R.id.iv_place_image)

        nameTextView.text = touristName


        val customDatabaseUrl = "https://mco3-final-default-rtdb.asia-southeast1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(customDatabaseUrl).getReference("attractions")


        if (touristName != null) {
            database.child(touristName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        val description = snapshot.child("description").getValue(String::class.java)
                        val imgPath = snapshot.child("imgPath").getValue(String::class.java)
                        val distance = snapshot.child("distance").getValue(Double::class.java)


                        Log.d("PlaceDetailsActivity", "Description: $description")
                        Log.d("PlaceDetailsActivity", "Image Path: $imgPath")
                        Log.d("PlaceDetailsActivity", "Distance: $distance")


                        detailsTextView.text = description ?: "Description not available"


                        distanceTextView.text = if (distance != null) {
                            "$distance Kilometers away"
                        } else {
                            "Distance not available"
                        }


                        if (imgPath != null) {

                            val resId = resources.getIdentifier(imgPath, "drawable", packageName)
                            if (resId != 0) {

                                imageView.setImageResource(resId)
                            } else {

                                Log.e("PlaceDetailsActivity", "Image resource not found for $imgPath")
                            }
                        } else {

                        }
                    } else {
                        detailsTextView.text = "No data found for $touristName"
                        distanceTextView.text = ""
                        Log.e("PlaceDetailsActivity", "No data found for $touristName")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    detailsTextView.text = "Failed to load details: ${error.message}"
                    distanceTextView.text = ""
                    Log.e("PlaceDetailsActivity", "Database error: ${error.message}")
                }
            })
        } else {
            detailsTextView.text = "Tourist name not provided"
            distanceTextView.text = ""
            Log.e("PlaceDetailsActivity", "Tourist name not provided")
        }
    }
}
