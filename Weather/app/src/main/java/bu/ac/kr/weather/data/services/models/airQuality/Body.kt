package bu.ac.kr.weather.data.services.models.airQuality

data class Body(
    val measuredValues: List<MeasuredValue>?,
    val numOfRows: Int?,
    val pageNo: Int?,
    val totalCount: Int?
)