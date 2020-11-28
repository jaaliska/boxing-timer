package by.itman.boxingtimer.providers

import android.util.Log
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class TimerProviderImpl @Inject constructor ( ): TimerProvider {
    init{
        Log.i("timerProvider", ": init")
    }

    var data = mutableMapOf(
        0 to TimerModel(
            id = 0,
            name = "Бокс",
            roundDuration = Duration.ofSeconds(180),
            restDuration =  Duration.ofSeconds(60),
            roundQuantity = 8,
            runUp = Duration.ofSeconds(20),
            noticeOfEndRound = Duration.ofSeconds(10),
            noticeOfEndRest = Duration.ofSeconds(5),
            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
            soundTypeOfStartRound = TimerSoundType.GONG,
            soundTypeOfStartRest = TimerSoundType.GONG
        ),
        1 to TimerModel(
            id = 1,
            name = "ММА",
            roundDuration = Duration.ofSeconds(300),
            restDuration =  Duration.ofSeconds(60),
            roundQuantity = 5,
            runUp = Duration.ofSeconds(20),
            noticeOfEndRound = Duration.ofSeconds(10),
            noticeOfEndRest = Duration.ofSeconds(5),
            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
            soundTypeOfStartRound = TimerSoundType.GONG,
            soundTypeOfStartRest = TimerSoundType.GONG
        ),
        2 to TimerModel(
            id = 2,
            name = "Лёгкий бокс",
            roundDuration = Duration.ofSeconds(120),
            restDuration =  Duration.ofSeconds(60),
            roundQuantity = 8,
            runUp = Duration.ofSeconds(20),
            noticeOfEndRound = Duration.ofSeconds(10),
            noticeOfEndRest = Duration.ofSeconds(5),
            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
            soundTypeOfStartRound = TimerSoundType.GONG,
            soundTypeOfStartRest = TimerSoundType.GONG
        ),
        3 to TimerModel(
            id = 3,
            name = "Табата",
            roundDuration = Duration.ofSeconds(20),
            restDuration =  Duration.ofSeconds(10),
            roundQuantity = 8,
            runUp = Duration.ofSeconds(20),
            noticeOfEndRound = Duration.ofSeconds(0),
            noticeOfEndRest = Duration.ofSeconds(0),
            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
            soundTypeOfStartRound = TimerSoundType.GONG,
            soundTypeOfStartRest = TimerSoundType.GONG
        ),
        4 to TimerModel(
            id = 4,
            name = "Новый таймер",
            roundDuration = Duration.ofSeconds(180),
            restDuration =  Duration.ofSeconds(60),
            roundQuantity = 8,
            runUp = Duration.ofSeconds(20),
            noticeOfEndRound = Duration.ofSeconds(10),
            noticeOfEndRest = Duration.ofSeconds(5),
            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
            soundTypeOfStartRound = TimerSoundType.GONG,
            soundTypeOfStartRest = TimerSoundType.GONG
        )
    )

    override fun getAll(): List<TimerModel> {
        return data.values.toList()
    }

    override fun getById(i: Int): TimerModel? {
        return data[i]
    }

    override fun save(timer: TimerModel): TimerModel {
        TODO()
    }

    override fun remove(timer: TimerModel) {
        data.remove(timer.id)
    }
}