package by.itman.boxingtimer.adapters;

import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerObserver;

interface TimerManager {
    fun subscribe(timerObserver: TimerObserver)
    fun unSubscribe(timerObserver: TimerObserver)
    fun startTimer()
    fun setTimer(timer: TimerModel)
    fun stopTimer()
}
