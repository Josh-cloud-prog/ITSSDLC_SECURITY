package com.mobdeve.s19.group10.mco2

data class TouristAttraction(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    var distance: String? = null
)