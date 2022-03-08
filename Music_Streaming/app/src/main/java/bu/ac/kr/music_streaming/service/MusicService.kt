package bu.ac.kr.music_streaming.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/v3/024b5832-8424-478b-99df-141ac01729cb")
    fun listMusics() : Call<MusicDto>
}