package bu.ac.kr.weather.data.services

import bu.ac.kr.weather.BuildConfig
import bu.ac.kr.weather.data.services.models.monitoringstation.MonitoringStation
import bu.ac.kr.weather.data.services.models.monitoringstation.MonitoringStationsResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {
    @GET("B552584/MsrstnInfoInqireSvc/getMsrstnList"+"?serviceKey=${BuildConfig.AIRKOREA_SERVICE_KEY}"+
    "&returnType=json")
    suspend fun  getNearbyMonitoringStation(
            @Query("tmX") tmX: Double,
            @Query("tmY") tmY: Double
        ): Response<MonitoringStationsResponse>
}