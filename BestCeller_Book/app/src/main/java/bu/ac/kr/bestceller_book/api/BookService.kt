package bu.ac.kr.bestceller_book.api

import bu.ac.kr.bestceller_book.model.BestSellerDto
import bu.ac.kr.bestceller_book.model.SearchBookDto
import retrofit2.http.GET
import retrofit2.http.Query


interface BookService {

    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String
    ): retrofit2.Call<SearchBookDto>
    @GET("/api/bestSeller.api?output=json&categoryId=100")
    fun getBestSellerBooks(
        @Query("key") apiKey: String
    ): retrofit2.Call<BestSellerDto>
}