package bu.ac.kr.toy_youtube.service

import android.provider.MediaStore
import bu.ac.kr.toy_youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/5723e03c-37ed-4c18-8d4d-6934b00845f9")
    fun listVideos(): Call<VideoDto>
}