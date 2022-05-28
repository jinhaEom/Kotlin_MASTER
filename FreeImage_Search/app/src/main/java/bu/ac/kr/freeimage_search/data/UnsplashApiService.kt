package bu.ac.kr.freeimage_search.data

import bu.ac.kr.freeimage_search.BuildConfig
import bu.ac.kr.freeimage_search.data.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET(
        "photos/random?"+
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}"+
                "&count=30"
    )

    suspend fun getRandomPhotos(
        @Query("query") query: String?
    ): Response<List<PhotoResponse>>
}