package by.itman.boxingtimer.data

import android.content.SharedPreferences
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import com.google.gson.Gson
import java.lang.IndexOutOfBoundsException
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsTimerProvider @Inject constructor(private val prefs: SharedPreferences) :
    TimerProvider {
    private val TIMER_KEY_PREFIX = "timer."
    private val TIMERS_KEY = "timers"
    private val ACTIVE_TIMER_KEY = "active_timer_model"

    private val gson: Gson = Gson()

    override fun getAll(): List<TimerModel> {
        return getTimerIds().mapNotNull { id -> getById(id) }
    }

    override fun getById(i: Int): TimerModel? {
        val timerJson = prefs.getString(getTimerPrefKey(i), null)
        return if (timerJson != null) deserializeTimer(timerJson) else null
    }

    override fun save(timer: TimerModel): TimerModel {
        if (timer.id != null) {
            if (!isTimerExist(timer.id)) {
                throw IndexOutOfBoundsException("Invalid Timer Id")
            }
            val editor = prefs.edit()
            val timerJson = serializeTimer(timer)
            editor.putString(getTimerPrefKey(timer.id), timerJson)
            editor.apply()
            return timer
        } else {
            val newTimer = TimerModel(
                getNextId(),
                timer.name,
                timer.roundDuration,
                timer.restDuration,
                timer.roundQuantity,
                timer.runUp,
                timer.noticeOfEndRound,
                timer.noticeOfEndRest,
                timer.soundTypeOfEndRoundNotice,
                timer.soundTypeOfEndRestNotice,
                timer.soundTypeOfStartRound,
                timer.soundTypeOfStartRest
            )
            val editor = prefs.edit()
            val timerJson = serializeTimer(newTimer)
            editor.putString(getTimerPrefKey(newTimer.id!!), timerJson)
            val timerIds = getTimerIds()
            timerIds.add(newTimer.id)
            saveTimerIds(editor, timerIds)
            editor.apply()
            return newTimer
        }
    }

    override fun remove(timer: TimerModel) {
        if (timer.id == null || !isTimerExist(timer.id)) {
            throw IndexOutOfBoundsException("Invalid Timer Id")
        }
        val editor = prefs.edit()
        editor.remove(getTimerPrefKey(timer.id))
        val timerIds = getTimerIds()
        timerIds.remove(timer.id)
        saveTimerIds(editor, timerIds)
        val activeTimer = getActiveTimer()
        if (activeTimer != null && activeTimer.id == timer.id) {
            editor.remove(ACTIVE_TIMER_KEY)
        }
        editor.apply()
    }

    override fun setActiveTimer(timer: TimerModel) {
        if (timer.id == null || !isTimerExist(timer.id)) {
            throw IndexOutOfBoundsException("Invalid Timer Id")
        }
        prefs.edit().putInt(ACTIVE_TIMER_KEY, timer.id).apply()
    }

    override fun getActiveTimer(): TimerModel? {
        if (!prefs.contains(ACTIVE_TIMER_KEY)) {
            return null
        }
        return getById(prefs.getInt(ACTIVE_TIMER_KEY, 0))
    }

    private fun isTimerExist(id: Int): Boolean {
        return prefs.contains(getTimerPrefKey(id))
    }

    private fun getTimerPrefKey(id: Int): String {
        return TIMER_KEY_PREFIX + id.toString()
    }

    private fun serializeTimer(timer: TimerModel): String {
        return gson.toJson(TimerModelWithoutDuration((timer)))
    }

    private fun deserializeTimer(string: String): TimerModel {
        return gson.fromJson(string, TimerModelWithoutDuration::class.java).getTimerModel()
    }

    private fun getTimerIds(): MutableList<Int> {
        val stringJson = prefs.getString(TIMERS_KEY, null)
        if (stringJson != null) {
            return gson.fromJson(stringJson, Array<Int>::class.java).toMutableList()
        }
        return mutableListOf()
    }

    private fun saveTimerIds(prefsEditor: SharedPreferences.Editor, ids: List<Int>) {
        prefsEditor.putString(TIMERS_KEY, gson.toJson(ids.toTypedArray()))
    }

    private fun getNextId(): Int {
        val ids = getTimerIds()
        val maxId = ids.max()
        return if (maxId == null) 1 else maxId + 1
    }

    data class TimerModelWithoutDuration(val timer: TimerModel) {
        private val id: Int? = timer.id
        private val name: String = timer.name
        private val roundDuration: Long = timer.roundDuration.toMillis()
        private val restDuration: Long = timer.restDuration.toMillis()
        private val roundQuantity: Int = timer.roundQuantity
        private val runUp: Long = timer.runUp.toMillis()
        private val noticeOfEndRound: Long = timer.noticeOfEndRound.toMillis()
        private val noticeOfEndRest: Long = timer.noticeOfEndRest.toMillis()
        private val soundTypeOfNoticeOfEndRound: TimerSoundType = timer.soundTypeOfEndRoundNotice
        private val soundTypeOfNoticeOfEndRest: TimerSoundType = timer.soundTypeOfEndRestNotice
        private val soundTypeOfStartRound: TimerSoundType = timer.soundTypeOfStartRound
        private val soundTypeOfStartRest: TimerSoundType = timer.soundTypeOfStartRest

        fun getTimerModel(): TimerModel {
            return TimerModel(
                id,
                name,
                Duration.ofMillis(roundDuration),
                Duration.ofMillis(restDuration),
                roundQuantity,
                Duration.ofMillis(runUp),
                Duration.ofMillis(noticeOfEndRound),
                Duration.ofMillis(noticeOfEndRest),
                soundTypeOfNoticeOfEndRound,
                soundTypeOfNoticeOfEndRest,
                soundTypeOfStartRound,
                soundTypeOfStartRest
            )
        }
    }
}