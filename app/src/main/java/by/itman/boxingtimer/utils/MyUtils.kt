package by.itman.boxingtimer.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration

class MyUtils {

    /**
     * The method converts insert Duration to String like "02:00"
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDuration(duration: Duration): String {
        val min = duration.toMinutes()
        val sec = duration.seconds - min * 60
        return (String.format("%02d", min) + ":" + String.format("%02d", sec))
    }
}