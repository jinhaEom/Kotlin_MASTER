package bu.ac.kr.weather.data.services.models.airQuality

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("measuredValues")
    val measuredValues: List<MeasuredValue>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)