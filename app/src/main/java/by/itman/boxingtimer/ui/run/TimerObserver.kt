package by.itman.boxingtimer.ui.run

import by.itman.boxingtimer.models.TimerPresentation
import java.time.Duration

interface TimerObserver {
    fun onCountDownTick(time: Duration)
    fun onRunUp(timer: TimerPresentation)
    fun onRoundStart(roundNumber: Int)
    fun onRestStart()
    fun onPauseTimer()
    fun onResumeTimer()
    fun onRestartTimer()
    fun onNoticeOfEndRest()
    fun onNoticeOfEndRound()
    fun onTimerFinished()
    fun onTimerStopped()
}