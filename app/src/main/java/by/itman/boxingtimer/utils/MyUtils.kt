package by.itman.boxingtimer.utils

import java.time.Duration

class MyUtils {

    /**
     * The method converts insert Duration to String like "02:00"
     */
    @Deprecated("Please use Duration.timerFormat()", ReplaceWith("duration.timerFormat()"))
    fun formatDuration(duration: Duration): String {
              return duration.timerFormat()
    }
}