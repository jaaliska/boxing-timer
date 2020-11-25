package by.itman.boxingtimer

import android.content.Context
import android.content.SharedPreferences
import by.itman.boxingtimer.adapters.TimerManagerImpl
import by.itman.boxingtimer.adapters.TimerManager
import by.itman.boxingtimer.presenters.EditingPresenter
import by.itman.boxingtimer.presenters.EditingPresenterImpl
import by.itman.boxingtimer.presenters.RunPresenter
import by.itman.boxingtimer.presenters.RunPresenterImpl
import by.itman.boxingtimer.providers.PrefsTimerProvider
import by.itman.boxingtimer.providers.TimerProvider
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
    abstract fun bindsEditionPresenter(editingPresenterImpl: EditingPresenterImpl): EditingPresenter

    @Binds
    abstract fun bindsRunPresenter(runPresenterImpl: RunPresenterImpl): RunPresenter

    @Singleton
    @Binds
    abstract fun bindsTimerManager(timerManager: TimerManagerImpl): TimerManager

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
        );
    }
}