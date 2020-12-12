package by.itman.boxingtimer.ui.run;

import by.itman.boxingtimer.models.TimerPresentation

interface TimerManager {
    fun subscribe(timerObserver: TimerObserver)
    fun unSubscribe(timerObserver: TimerObserver)
    fun run(timer: TimerPresentation)
    fun pause()
    fun resume()
    fun restart()
    fun finish()
    fun stop()
}
