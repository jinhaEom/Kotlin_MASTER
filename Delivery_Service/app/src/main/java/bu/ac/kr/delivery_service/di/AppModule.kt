package bu.ac.kr.delivery_service.di

import android.app.Activity
import android.app.Presentation
import androidx.core.view.MotionEventCompat.getSource
import bu.ac.kr.delivery_service.api.SweetTrackerApi
import bu.ac.kr.delivery_service.api.Url
import bu.ac.kr.delivery_service.db.AppDatabase
import bu.ac.kr.delivery_service.entity.TrackingInformation
import bu.ac.kr.delivery_service.entity.TrackingItem
import bu.ac.kr.delivery_service.preference.PreferenceManager
import bu.ac.kr.delivery_service.preference.SharedPreferenceManager
import bu.ac.kr.delivery_service.presentation.addtrackingitem.AddTrackingItemFragment
import bu.ac.kr.delivery_service.presentation.addtrackingitem.AddTrackingItemPresenter
import bu.ac.kr.delivery_service.presentation.addtrackingitem.AddTrackingItemsContract
import bu.ac.kr.delivery_service.presentation.trackinghistory.TrackingHistoryContract
import bu.ac.kr.delivery_service.presentation.trackinghistory.TrackingHistoryFragment
import bu.ac.kr.delivery_service.presentation.trackinghistory.TrackingHistoryPresenter
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsContract
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsFragment
import bu.ac.kr.delivery_service.presentation.trackingitems.TrackingItemsPresenter
import bu.ac.kr.delivery_service.repository.*
import bu.ac.kr.delivery_service.work.AppWorkerFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {
    single { Dispatchers.IO}

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().trackingItemDao() }
    single { get<AppDatabase>().shippingCompanyDao() }

    //Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<SweetTrackerApi> {
        Retrofit.Builder().baseUrl(Url.SWEET_TRACKER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }
    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }

    // Repository
//    single<TrackingItemRepository> { TrackingItemRepositoryStub() }
    single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(), get(), get()) }
    single<ShippingCompanyRepository> { ShippingCompanyRepositoryImpl(get(), get(), get(), get()) }

    //work
    single{ AppWorkerFactory(get(),get()) }
    //Presentation
    scope<TrackingItemsFragment> {
        scoped<TrackingItemsContract.Presenter> { TrackingItemsPresenter(getSource(), get()) }
    }
    scope<AddTrackingItemFragment> {
        scoped<AddTrackingItemsContract.Presenter> {
            AddTrackingItemPresenter(getSource(), get(), get())
        }
    }
    scope<TrackingHistoryFragment> {
        scoped<TrackingHistoryContract.Presenter> { (trackingItem: TrackingItem, trackingInformation: TrackingInformation) ->
            TrackingHistoryPresenter(getSource(), get(), trackingItem, trackingInformation)
        }
    }
}