package by.itman.boxingtimer

import android.app.Application
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.providers.TimerProviderImpl
import dagger.Binds
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {}