package by.itman.boxingtimer

import android.app.Application
import android.content.Context
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.providers.TimerProviderImpl
import dagger.Binds
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}