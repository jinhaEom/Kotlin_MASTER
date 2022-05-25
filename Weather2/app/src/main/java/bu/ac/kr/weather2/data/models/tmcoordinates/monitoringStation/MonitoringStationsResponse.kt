package bu.ac.kr.weather2.data.models.tmcoordinates.monitoringStation


import com.google.gson.annotations.SerializedName

data class MonitoringStationsResponse(
    @SerializedName("response")
    val response: Response?
)