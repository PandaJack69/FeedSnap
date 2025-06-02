package com.example.feedsnap

import android.app.Application
import com.example.feedsnap.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FeedSnapApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FeedSnapApp)
            modules(AppModule.module)
        }
    }
}

//class App {
//}