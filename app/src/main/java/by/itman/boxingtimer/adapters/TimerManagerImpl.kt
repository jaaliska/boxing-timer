package by.itman.boxingtimer.adapters

import android.os.Build
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import by.itman.boxingtimer.models.TimerObserver
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManagerImpl @Inject constructor ():
    TimerManager {
private lateinit var countDownTimer: AdvancedCountDownTimer
    val timerObservers = mutableListOf<TimerObserver>()

    override fun startTimer() {
        var t = SystemClock.elapsedRealtime()
        countDownTimer = object : AdvancedCountDownTimer(100000, 1000, 10) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                for (observer in timerObservers) {
                    observer.onTick(Duration.ofMillis(millisUntilFinished))
                    Log.i("timer going", millisUntilFinished.toString())
                }
            }
            override fun onFinish() {
                Log.i("timer finish", (SystemClock.elapsedRealtime() - t).toString())
            }
        }.start()

    }

    override fun subscribe(timerObserver: TimerObserver) {
        timerObservers.add(timerObserver)
    }

    override fun unSubscribe(timerObserver: TimerObserver) {
        timerObservers.remove(timerObserver)
    }

}
