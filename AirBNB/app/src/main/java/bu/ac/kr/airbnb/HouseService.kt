package bu.ac.kr.airbnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("v3/db6fab80-3390-470c-97c1-6adf3d64c128")
    fun getHouseList(): Call<HouseDto>
}