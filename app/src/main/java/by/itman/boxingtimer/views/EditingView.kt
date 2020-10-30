package by.itman.boxingtimer.views

import android.content.Context
import by.itman.boxingtimer.models.TimerSoundType
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.time.Duration

@StateStrategyType(AddToEndSingleStrategy::class)
interface EditingView: MvpView {
    fun displayTimerFields(timerPresentation: TimerPresentation)
    fun showDurationDialog(title: Int, time: Duration, consumer: (Duration) -> Unit)
    fun showNumberDialog(title: Int, number: Int, consumer: (Int) -> Unit)
    fun showSoundTypeDialog(title: Int, sound: TimerSoundType, consumer: (TimerSoundType) -> Unit)
    fun showStringDialog(title: Int, string: String, consumer: (String) -> Unit)
}