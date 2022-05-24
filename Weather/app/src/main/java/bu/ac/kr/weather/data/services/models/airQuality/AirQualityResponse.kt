package bu.ac.kr.weather.data.services.models.airQuality

import com.google.gson.annotations.SerializedName

data class AirQualityResponse(
    @SerializedName("response")
    val response: Response?=null
)