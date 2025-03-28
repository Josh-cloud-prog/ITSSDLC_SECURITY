package com.mobdeve.s19.group10.mco2

import Itinerary
import ItineraryDay
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateItineraryActivity : AppCompatActivity() {

    private lateinit var spinnerDays: Spinner
    private lateinit var dayContainer: LinearLayout
    private lateinit var btnSave: Button
    private lateinit var itinerary: Itinerary
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_itinerary)


        database = FirebaseDatabase.getInstance("https://mco3-final-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        spinnerDays = findViewById(R.id.spinner_days)
        dayContainer = findViewById(R.id.ll_day_container)
        btnSave = findViewById(R.id.btn_save_itinerary)


        spinnerDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                dayContainer.removeAllViews()

                val daysCount = position + 1
                val daysList = mutableListOf<ItineraryDay>()

                for (day in 1..daysCount) {
                    val dayView = LayoutInflater.from(this@CreateItineraryActivity)
                        .inflate(R.layout.item_day_layout, dayContainer, false)

                    val tvDay = dayView.findViewById<TextView>(R.id.tv_day)
                    tvDay.text = "Day $day"

                    val llTimeContainer = dayView.findViewById<LinearLayout>(R.id.ll_time_container)


                    val btnAddTime = dayView.findViewById<ImageButton>(R.id.btn_add_time)
                    btnAddTime.setOnClickListener {

                        val timeActivityView = LayoutInflater.from(this@CreateItineraryActivity)
                            .inflate(R.layout.item_time_activity, null)

                        llTimeContainer.addView(timeActivityView)
                    }


                    dayContainer.addView(dayView)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        btnSave.setOnClickListener {
            val destination = findViewById<EditText>(R.id.et_destination).text.toString()
            val stayingPeriod = findViewById<EditText>(R.id.et_stayingPeriod).text.toString()
            val budget = findViewById<EditText>(R.id.et_budget).text.toString()

            if (destination.isNotEmpty() && stayingPeriod.isNotEmpty() && budget.isNotEmpty()) {
                val daysList = mutableListOf<ItineraryDay>()


                for (i in 0 until dayContainer.childCount) {
                    val dayView = dayContainer.getChildAt(i)

                    val llTimeContainer = dayView.findViewById<LinearLayout>(R.id.ll_time_container)

                    val timesForDay = mutableListOf<String>()
                    val activitiesForDay = mutableListOf<String>()


                    for (j in 0 until llTimeContainer.childCount) {
                        val timeActivityView = llTimeContainer.getChildAt(j)

                        val etTime = timeActivityView.findViewById<EditText>(R.id.et_time)
                        val etActivity = timeActivityView.findViewById<EditText>(R.id.et_activity)

                        val time = etTime.text.toString()
                        val activity = etActivity.text.toString()

                        if (time.isNotEmpty() && activity.isNotEmpty()) {
                            timesForDay.add(time)
                            activitiesForDay.add(activity)
                        }
                    }

                    val itineraryDay = ItineraryDay(
                        day = "Day ${i + 1}",
                        time = timesForDay,
                        activity = activitiesForDay
                    )

                    daysList.add(itineraryDay)
                }


                itinerary = Itinerary(
                    destination = destination,
                    stayingPeriod = stayingPeriod,
                    budget = budget,
                    days = daysList
                )


                val itineraryId = database.child("itineraries").push().key
                if (itineraryId != null) {
                    database.child("itineraries").child(itineraryId).setValue(itinerary)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Itinerary saved successfully!", Toast.LENGTH_SHORT).show()


                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save itinerary.", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
