package by.itman.boxingtimer.providers

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import by.itman.boxingtimer.models.TimerModel
import com.google.gson.Gson
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsTimerProvider @Inject constructor (private val prefs: SharedPreferences ): TimerProvider {
    private val TIMER_KEY_PREFIX = "timer."
    private val TIMERS_KEY = "timers"

    private val gson: Gson = Gson()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAll(): List<TimerModel> {
        return getTimerIds().mapNotNull { id -> getById(id) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getById(i: Int): TimerModel? {
        val timerJson = prefs.getString(getTimerPrefKey(i), null)
        return if (timerJson != null) deserializeTimer(timerJson) else null
    }

    override fun save(timer: TimerModel) : TimerModel {
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
                timer.soundType
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
        editor.apply()
    }

    private fun isTimerExist(id: Int): Boolean {
        return prefs.contains(getTimerPrefKey(id))
    }

    private fun getTimerPrefKey(id: Int): String {
        return TIMER_KEY_PREFIX + id.toString()
    }

    private fun serializeTimer(timer: TimerModel): String {
        return gson.toJson(timer)
    }

    private fun deserializeTimer(string: String): TimerModel {
        return gson.fromJson(string, TimerModel::class.java)
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
}