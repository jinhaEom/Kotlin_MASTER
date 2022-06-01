package bu.ac.kr.treat_repo.utility

import bu.ac.kr.treat_repo.data.response.GithubAccessTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @POST("login/auth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Field("client_id") clientId:String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code : String
    ): Response<GithubAccessTokenResponse>

}