package bu.ac.kr.bestseller_interpark.api

import bu.ac.kr.bestseller_interpark.model.BestSellerDto
import bu.ac.kr.bestseller_interpark.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String
    ): Call<SearchBookDto>
    @GET("/api/bestSeller.api?output=json&categoryId=100")
    fun getBestSeller(
        @Query("key") apiKey: String
    ): Call<BestSellerDto>
}