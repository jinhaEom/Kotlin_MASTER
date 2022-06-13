package bu.ac.kr.todo_app

import android.app.Application
import bu.ac.kr.todo_app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class todoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@todoApplication)
            modules(appModule)
        }
    }
}