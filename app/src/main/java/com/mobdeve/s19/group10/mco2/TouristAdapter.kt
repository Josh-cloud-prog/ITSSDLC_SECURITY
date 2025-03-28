package com.mobdeve.s19.group10.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TouristAdapter(
    private val touristAttractions: List<TouristAttraction>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TouristAdapter.TouristViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(touristAttraction: TouristAttraction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tourist_attraction, parent, false)
        return TouristViewHolder(view)
    }

    override fun onBindViewHolder(holder: TouristViewHolder, position: Int) {
        val attraction = touristAttractions[position]
        holder.bind(attraction)
    }

    override fun getItemCount(): Int = touristAttractions.size

    inner class TouristViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val attractionName: TextView = itemView.findViewById(R.id.tv_attraction_name)
        private val distance: TextView = itemView.findViewById(R.id.tv_distance)


        fun bind(touristAttraction: TouristAttraction) {
            attractionName.text = touristAttraction.name
            distance.text = touristAttraction.distance


            itemView.setOnClickListener {
                listener.onItemClick(touristAttraction)
            }
        }
    }
}
