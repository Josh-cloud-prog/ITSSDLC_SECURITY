package com.mobdeve.s19.group10.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Accommodation(
    val name: String,
    val rating: String,
    val location: String,
    val imageResId: Int
)

class AccommodationAdapter(private val accommodations: List<Accommodation>) : RecyclerView.Adapter<AccommodationAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accommodationImage: ImageView = view.findViewById(R.id.accommodationImage)
        val accommodationName: TextView = view.findViewById(R.id.accommodationName)
        val accommodationRating: TextView = view.findViewById(R.id.accommodationRating)
        val accommodationLocation: TextView = view.findViewById(R.id.accommodationLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.accommodation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accommodation = accommodations[position]
        holder.accommodationName.text = accommodation.name
        holder.accommodationRating.text = "Rating: ${accommodation.rating}"
        holder.accommodationLocation.text = accommodation.location
        holder.accommodationImage.setImageResource(accommodation.imageResId)
    }

    override fun getItemCount(): Int {
        return accommodations.size
    }
}
