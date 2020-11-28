package by.itman.boxingtimer.models

import java.time.Duration


data class TimerModel(
    val id: Int?,
    var name: String,
    var roundDuration: Duration,
    var restDuration: Duration,
    var roundQuantity: Int,
    var runUp: Duration,
    var noticeOfEndRound: Duration,
    var noticeOfEndRest: Duration,
    internal var soundTypeOfEndRoundNotice: TimerSoundType,
    internal var soundTypeOfEndRestNotice: TimerSoundType,
    internal var soundTypeOfStartRound: TimerSoundType,
    internal var soundTypeOfStartRest: TimerSoundType
) {



    fun getTrainingDuration(): Long {
        return roundDuration.plus(restDuration).multipliedBy(roundQuantity.toLong()).toMinutes()
    }

    override fun toString(): String {
        return name
    }
}