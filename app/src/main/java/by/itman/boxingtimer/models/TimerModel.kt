package by.itman.boxingtimer.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration


data class TimerModel(
    val id: Int?,
    var name: String,

    var roundDuration: Duration,

    var restDuration: Duration,

    var roundQuantity: Int,
    var runUp: Duration,
    var noticeOfEndRound: Duration,
    internal var soundType: TimerSoundType
) {



    @RequiresApi(Build.VERSION_CODES.O)
    fun getTrainingDuration(): Long {
        return roundDuration.plus(restDuration).multipliedBy(roundQuantity.toLong()).toMinutes()
    }

    override fun toString(): String {
        return name
    }
}