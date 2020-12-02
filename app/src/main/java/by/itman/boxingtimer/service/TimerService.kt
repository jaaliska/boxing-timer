package by.itman.boxingtimer.service

import by.itman.boxingtimer.ui.run.TimerObserver

interface TimerService {
    fun subscribe(timerObserver: TimerObserver)
    fun unSubscribe(timerObserver: TimerObserver)
}