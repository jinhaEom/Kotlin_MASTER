package bu.ac.kr.delivery_service

import android.app.Application
import androidx.work.Configuration
import bu.ac.kr.delivery_service.di.appModule
import bu.ac.kr.delivery_service.work.AppWorkerFactory
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.android.ext.android.inject

class TrackerApplication : Application() , Configuration.Provider {
    private val workerFactory : AppWorkerFactory by inject()

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
            androidContext(this@TrackerApplication)
            modules(appModule)
        }
    }
    override fun getWorkManagerConfiguration() : Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(
                if(BuildConfig.DEBUG){
                    android.util.Log.DEBUG
                }else{
                    android.util.Log.INFO
                }
            )
            .setWorkerFactory(workerFactory)
            .build()
}