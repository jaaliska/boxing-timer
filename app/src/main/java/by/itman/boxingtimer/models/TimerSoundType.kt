package by.itman.boxingtimer.models

import androidx.annotation.IdRes
import by.itman.boxingtimer.App
import by.itman.boxingtimer.R

enum class TimerSoundType {
    GONG,
    GREAT_GONG,
    LOW_GONG,
    BELL,
    WARNING;

    companion object {
        @IdRes
        fun getResource(sound: TimerSoundType): Int {
            return when (sound) {
                GONG -> R.raw.gong
                GREAT_GONG -> R.raw.great_gong
                LOW_GONG -> R.raw.low_gong
                BELL -> R.raw.bell
                WARNING -> R.raw.warning
            }
        }

        fun getTitle(sound: TimerSoundType): String {
            return when (sound) {
                GONG -> App.applicationContext().resources.getString(R.string.timer_sound_gong_title)
                GREAT_GONG -> App.applicationContext().resources.getString(R.string.timer_sound_great_gong_title)
                LOW_GONG -> App.applicationContext().resources.getString(R.string.timer_sound_low_gong_title)
                BELL -> App.applicationContext().resources.getString(R.string.timer_sound_bell_title)
                WARNING -> App.applicationContext().resources.getString(R.string.timer_sound_warning_title)
            }
        }
    }
}