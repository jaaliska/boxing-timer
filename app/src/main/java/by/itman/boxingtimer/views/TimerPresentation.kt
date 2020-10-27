package by.itman.boxingtimer.views

import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration

interface TimerPresentation {
    fun getName(): String
    fun getRoundDuration(): Duration
    fun getRestDuration(): Duration
    fun getRoundQuantity(): Int
    fun getRunUp(): Duration
    fun getNoticeOfEndRound(): Duration
    fun getSoundType(): TimerSoundType
}