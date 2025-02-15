data class ParsingData(
    val address: String = "",
    val days: List<Day> = emptyList(),
)

data class Day(
    val datetime: String = "",
    val tempmax: Double = 0.0,
    val tempmin: Double = 0.0,
    val temp: Double = 0.0,
    val hours: List<Hour> = emptyList<Hour>(),
)

data class Hour(
    val datetime: String = "",
    val temp: Double = 0.0,
)
