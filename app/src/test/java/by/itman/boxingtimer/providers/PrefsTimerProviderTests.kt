package by.itman.boxingtimer.providers

import android.content.SharedPreferences
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import org.junit.Assert
import org.junit.Test
import java.time.Duration
import javax.inject.Inject

class PrefsTimerProviderTests @Inject constructor (private val prefs: SharedPreferences ) {
    private val prefsTimerProvider: PrefsTimerProvider = PrefsTimerProvider(prefs)

    fun createMap(): List<TimerModel> {
        return listOf(
            TimerModel(id = 0, name = "Бокс", roundDuration = Duration.ofSeconds(180),
                restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG ),
            TimerModel(id = 1, name = "ММА", roundDuration = Duration.ofSeconds(300),
                restDuration =  Duration.ofSeconds(60), roundQuantity = 5, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG ),
            TimerModel(id = 2, name = "Лёгкий бокс", roundDuration = Duration.ofSeconds(120),
                restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG ),
            TimerModel(id = 3, name = "Табата", roundDuration = Duration.ofSeconds(20),
                restDuration =  Duration.ofSeconds(10), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(0), soundType = TimerSoundType.GONG ),
            TimerModel(id = 4, name = "Новый таймер", roundDuration = Duration.ofSeconds(180),
                restDuration =  Duration.ofSeconds(60), roundQuantity = 8, runUp = Duration.ofSeconds(20), noticeOfEndRound = Duration.ofSeconds(10), soundType = TimerSoundType.GONG )
        )
    }


    @Test
    fun testGetAll() {
        val result: List<TimerModel> = prefsTimerProvider.getAll()
        val expected = createMap()
        Assert.assertEquals(result, expected)
    }
}