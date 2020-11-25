package by.itman.boxingtimer.models

interface TimerService {
    fun subscribe(timerObserver: TimerObserver)
    fun unSubscribe(timerObserver: TimerObserver)
}