package by.itman.boxingtimer.data

import android.content.SharedPreferences
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration
import javax.inject.Inject

class ApplicationDefaultTimers
@Inject constructor(val timerProvider: TimerProvider, val prefs: SharedPreferences) {

    fun initializeDefaultTimers() {
        if (!prefs.contains("defaults_initialized")) {
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Бокс",
                    roundDuration = Duration.ofSeconds(180),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(30),
                    noticeOfEndRest = Duration.ofSeconds(10),
                    soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                    soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                    soundTypeOfStartRound = TimerSoundType.GONG,
                    soundTypeOfStartRest = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Лёгкий бокс",
                    roundDuration = Duration.ofSeconds(120),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(30),
                    noticeOfEndRest = Duration.ofSeconds(10),
                    soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                    soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                    soundTypeOfStartRound = TimerSoundType.GONG,
                    soundTypeOfStartRest = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "ММА",
                    roundDuration = Duration.ofSeconds(300),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 5,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(30),
                    noticeOfEndRest = Duration.ofSeconds(10),
                    soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                    soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                    soundTypeOfStartRound = TimerSoundType.GONG,
                    soundTypeOfStartRest = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Табата",
                    roundDuration = Duration.ofSeconds(20),
                    restDuration = Duration.ofSeconds(10),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(0),
                    noticeOfEndRest = Duration.ofSeconds(0),
                    soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                    soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                    soundTypeOfStartRound = TimerSoundType.GONG,
                    soundTypeOfStartRest = TimerSoundType.GONG
                )
            )
            prefs.edit().putBoolean("defaults_initialized", true).apply()
        }
    }
}