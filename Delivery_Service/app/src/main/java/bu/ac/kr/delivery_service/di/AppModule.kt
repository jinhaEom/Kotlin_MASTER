package bu.ac.kr.delivery_service.di

import bu.ac.kr.delivery_service.api.Url
import bu.ac.kr.delivery_service.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {
    single { Dispatchers.IO}

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao()}

    //Api
    single {
        OkHttpClient()
            .newBuilder().addInterceptor(
                HttpLoggingInterceptor().apply{
                    level = if(BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    }else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
   
}