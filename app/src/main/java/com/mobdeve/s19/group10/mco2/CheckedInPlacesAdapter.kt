package com.mobdeve.s19.group10.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckedInPlacesAdapter(private val places: List<String>) :
    RecyclerView.Adapter<CheckedInPlacesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeTextView: TextView = itemView.findViewById(R.id.tv_place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checked_in_place_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeTextView.text = places[position]
    }

    override fun getItemCount(): Int {
        return places.size
    }
}
