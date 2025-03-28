package com.mobdeve.s19.group10.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Restaurant(val name: String, val rating: String, val location: String, val imageResId: Int)

class RestaurantAdapter(private val restaurantList: List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.restaurantName)
        val ratingTextView: TextView = itemView.findViewById(R.id.restaurantRating)
        val locationTextView: TextView = itemView.findViewById(R.id.restaurantLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.nameTextView.text = restaurant.name
        holder.ratingTextView.text = "Rating: ${restaurant.rating}"
        holder.locationTextView.text = "Location: ${restaurant.location}"
    }

    override fun getItemCount(): Int = restaurantList.size
}
