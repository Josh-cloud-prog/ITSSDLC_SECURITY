package com.mobdeve.s19.group10.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Attraction(
    val name: String,
    val location: String,
    val imageResId: Int
)

class AttractionAdapter(private val attractions: List<Attraction>) : RecyclerView.Adapter<AttractionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val attractionImage: ImageView = view.findViewById(R.id.attractionImage)
        val attractionName: TextView = view.findViewById(R.id.attractionName)
        val attractionLocation: TextView = view.findViewById(R.id.attractionLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attraction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attraction = attractions[position]
        holder.attractionName.text = attraction.name
        holder.attractionLocation.text = attraction.location
        holder.attractionImage.setImageResource(attraction.imageResId)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }
}
