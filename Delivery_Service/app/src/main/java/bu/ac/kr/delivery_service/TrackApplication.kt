package bu.ac.kr.delivery_service

import android.app.Application
import bu.ac.kr.delivery_service.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TrackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(
                if(BuildConfig.DEBUG){
                    Level.DEBUG
                }else{
                    Level.NONE
                }
            )
            androidContext(this@TrackApplication)
            modules(appModule)
        }
    }
}