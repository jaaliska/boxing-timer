package by.itman.boxingtimer.service;

import by.itman.boxingtimer.models.TimerPresentation
import by.itman.boxingtimer.ui.run.TimerObserver

interface TimerService {
    fun subscribe(timerObserver: TimerObserver)
    fun unSubscribe(timerObserver: TimerObserver)
    fun run(timer: TimerPresentation)
    fun pause()
    fun resume()
    fun restart()
    fun finish()
    fun stop()
}