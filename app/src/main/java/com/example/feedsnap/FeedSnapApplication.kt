package com.example.feedsnap

import android.app.Application
import com.example.feedsnap.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FeedSnapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@FeedSnapApplication)
            // Load your modules
            modules(AppModule.module)
        }
    }
}