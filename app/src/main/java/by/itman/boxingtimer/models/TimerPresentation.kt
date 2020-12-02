package by.itman.boxingtimer.models

import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration

interface TimerPresentation {
    fun getName(): String
    fun getRoundDuration(): Duration
    fun getRestDuration(): Duration
    fun getRoundQuantity(): Int
    fun getRunUp(): Duration
    fun getNoticeOfEndRound(): Duration
    fun getNoticeOfEndRest(): Duration
    fun getSoundTypeOfEndRoundNotice(): TimerSoundType
    fun getSoundTypeOfEndRestNotice(): TimerSoundType
    fun getSoundTypeOfStartRound(): TimerSoundType
    fun getSoundTypeOfStartRest(): TimerSoundType
}