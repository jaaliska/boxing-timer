package by.itman.boxingtimer.ui.editing

import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.models.TimerPresentation
import java.time.Duration


interface EditingView {
    fun displayTimerFields(timerPresentation: TimerPresentation)
    fun showDurationDialog(title: Int, time: Duration, consumer: (Duration) -> Unit)
    fun showNumberDialog(title: Int, number: Int, consumer: (Int) -> Unit)
    fun showSoundTypeDialog(title: Int, sound: TimerSoundType, consumer: (TimerSoundType) -> Unit)
    fun showStringDialog(title: Int, string: String, consumer: (String) -> Unit)
}