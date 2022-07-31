package bu.ac.kr.subway_arriveinfo.di

import android.app.Activity
import bu.ac.kr.subway_arriveinfo.data.api.StationApi
import bu.ac.kr.subway_arriveinfo.data.api.StationStorageApi
import bu.ac.kr.subway_arriveinfo.data.db.entity.AppDatabase
import bu.ac.kr.subway_arriveinfo.preferences.PreferenceManager
import bu.ac.kr.subway_arriveinfo.preferences.SharedPreferenceManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module{

    // single -> 앱이 실행되는 동안 계속 유지되는 싱글톤 객체를 생성
    single { Dispatchers.IO}

    //Database
    single { AppDatabase.build(androidApplication())}
    single { get<AppDatabase>().stationDao()}

    //Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE)}
    single<PreferenceManager> { SharedPreferenceManager(get())}

    //API
    single<StationApi>{ StationStorageApi(Firebase.storage)}

}
