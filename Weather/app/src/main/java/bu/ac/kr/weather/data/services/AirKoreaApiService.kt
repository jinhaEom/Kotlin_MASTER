package bu.ac.kr.weather.data.services

import bu.ac.kr.weather.BuildConfig
import bu.ac.kr.weather.data.services.models.airQuality.AirQualityResponse
import bu.ac.kr.weather.data.services.models.monitoringstation.MonitoringStation
import bu.ac.kr.weather.data.services.models.monitoringstation.MonitoringStationsResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {
    @GET("B552584/MsrstnInfoInqireSvc/getMsrstnList" +
            "?serviceKey=${BuildConfig.AIR_KOREA_SERVICE_KEY}" +
            "&returnType=json")
    suspend fun getNearbyMonitoringStation(
        @Query("tmX") tmX: Double,
        @Query("tmY") tmY: Double,
    ): Response<MonitoringStationsResponse>


    @GET("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"+
            "?serviceKey=${BuildConfig.AIR_KOREA_SERVICE_KEY}"+"&returnType=json"+"&dataTerm=DAILY"
    +"&ver=1.3")
    suspend fun getRealtimeAirQualites(
        @Query("stationName")stationName: String

    ): Response<AirQualityResponse>
}