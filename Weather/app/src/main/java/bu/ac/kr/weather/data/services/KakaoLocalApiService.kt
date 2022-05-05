package bu.ac.kr.weather.data.services

import bu.ac.kr.weather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoLocalApiService {
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("v2/local/geo/transcoord.json?output_coord=TM")
    suspend fun  getTmCoordinates(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    )
}