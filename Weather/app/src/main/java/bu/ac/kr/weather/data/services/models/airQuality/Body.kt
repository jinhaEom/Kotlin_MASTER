package bu.ac.kr.weather.data.services.models.airQuality

data class Body(
    val items: List<Item>?,
    val numOfRows: Int?,
    val pageNo: Int?,
    val totalCount: Int?
)