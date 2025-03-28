package com.mobdeve.s19.group10.mco2

import Itinerary
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItineraryAdapter(private val itineraries: MutableList<Itinerary>) : RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder>() {

    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://mco3-final-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_itinerary, parent, false)
        return ItineraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        val itinerary = itineraries[position]
        holder.tvDestination.text = itinerary.destination
        holder.tvStayingPeriod.text = itinerary.stayingPeriod
    }

    override fun getItemCount(): Int {
        return itineraries.size
    }

    class ItineraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDestination: TextView = itemView.findViewById(R.id.tv_destination)
        val tvStayingPeriod: TextView = itemView.findViewById(R.id.tv_staying_period)
    }


    fun loadItinerariesFromFirebase() {
        databaseReference.child("itineraries").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itineraries.clear()
                for (data in snapshot.children) {

                    val itinerary = data.getValue(Itinerary::class.java)
                    itinerary?.let {
                        itineraries.add(it)
                    }
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e("ItineraryAdapter", "Failed to read value: ${error.toException()}")
            }
        })
    }
}
