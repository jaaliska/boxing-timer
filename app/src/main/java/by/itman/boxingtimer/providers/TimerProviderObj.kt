package by.itman.boxingtimer.providers

import android.os.Build
import androidx.annotation.RequiresApi
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration

object TimerProviderObj {
    @RequiresApi(Build.VERSION_CODES.O)
    var timer1: TimerModel = TimerModel(id = 0, name = "Бокс", roundDuration = Duration.ofSeconds(180),
        restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG )
    @RequiresApi(Build.VERSION_CODES.O)
    var timer2: TimerModel = TimerModel(id = 1, name = "ММА", roundDuration = Duration.ofSeconds(300),
        restDuration =  Duration.ofSeconds(60), roundQuantity = 5, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG )
    @RequiresApi(Build.VERSION_CODES.O)
    var timer3: TimerModel = TimerModel(id = 2, name = "Лёгкий бокс", roundDuration = Duration.ofSeconds(120),
        restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG )
    @RequiresApi(Build.VERSION_CODES.O)
    var timer4: TimerModel = TimerModel(id = 3, name = "Табата", roundDuration = Duration.ofSeconds(20),
        restDuration =  Duration.ofSeconds(10), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(0), soundType = TimerSoundType.GONG )
    @RequiresApi(Build.VERSION_CODES.O)
    var timer5: TimerModel = TimerModel(id = 4, name = "Новый таймер", roundDuration = Duration.ofSeconds(180),
    restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG )


}