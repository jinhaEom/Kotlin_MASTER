package bu.ac.kr.weather2.data.services

import retrofit2.http.GET
import retrofit2.http.Headers

interface KakaoLocalApiService {

    @Headers("Authorization: KakaoAK")
    @GET("v2/local/geo/transcoord.json?=output_coord=TM")
    suspend fun getTmCoordinates()

}