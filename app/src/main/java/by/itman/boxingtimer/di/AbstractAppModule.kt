package by.itman.boxingtimer.di

import android.content.Context
import android.content.SharedPreferences
import by.itman.boxingtimer.ui.editing.EditingPresenter
import by.itman.boxingtimer.ui.editing.EditingPresenterImpl
import by.itman.boxingtimer.data.PrefsTimerProvider
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.service.TimerService
import by.itman.boxingtimer.service.TimerServiceImpl
import by.itman.boxingtimer.ui.main.MainPresenter
import by.itman.boxingtimer.ui.main.MainPresenterImpl
import by.itman.boxingtimer.ui.run.*
import by.itman.boxingtimer.ui.settings.SettingsPresenter
import by.itman.boxingtimer.ui.settings.SettingsPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module(includes = [AppModule::class])
abstract class AbstractAppModule {
    @Singleton
    @Binds
    abstract fun bindsTimerProvider(timerProvider: PrefsTimerProvider): TimerProvider

    @Binds
    abstract fun bindsMainPresenter(mainPresenterImpl: MainPresenterImpl): MainPresenter

    @Binds
    abstract fun bindsSettingsPresenter(settingsPresenterImpl: SettingsPresenterImpl): SettingsPresenter

    @Binds
    abstract fun bindsEditionPresenter(editingPresenterImpl: EditingPresenterImpl): EditingPresenter

    @Binds
    abstract fun bindsRunPresenter(runPresenterImpl: RunPresenterImpl): RunPresenter

    @Singleton
    @Binds
    abstract fun bindsTimerManager(timerManager: TimerServiceImpl): TimerService
}

@InstallIn(ApplicationComponent::class)
@Module
internal class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName + "_custom_preferences",
            Context.MODE_PRIVATE
        )
    }
}