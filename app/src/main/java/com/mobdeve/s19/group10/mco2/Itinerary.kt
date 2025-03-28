data class ItineraryDay(
    val day: String = "",
    val time: List<String> = listOf(),
    val activity: List<String> = listOf(),
)

data class Itinerary(
    val days: List<ItineraryDay> = listOf(),
    val stayingPeriod: String = "",
    val budget: String = "",
    val destination: String = "",
)