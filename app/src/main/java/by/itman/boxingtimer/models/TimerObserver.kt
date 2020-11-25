package by.itman.boxingtimer.models

import by.itman.boxingtimer.views.TimerPresentation
import java.time.Duration

interface TimerObserver {
    fun onTick(time: Duration)
    fun getTimer(): TimerPresentation
}