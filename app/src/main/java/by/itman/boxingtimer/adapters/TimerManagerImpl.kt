package by.itman.boxingtimer.adapters

import android.os.SystemClock
import android.util.Log
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerObserver
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManagerImpl @Inject constructor ():
    TimerManager {
private lateinit var countDownTimer: AdvancedCountDownTimer
    private val timerObservers = mutableListOf<TimerObserver>()
    private lateinit var actualTimer: TimerModel

    override fun startTimer() {
        val t = SystemClock.elapsedRealtime()
        countDownTimer = object : AdvancedCountDownTimer(actualTimer.roundDuration.toMillis(), 1000) {
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

    override fun setTimer(timer: TimerModel) {
        actualTimer = timer
    }

    override fun subscribe(timerObserver: TimerObserver) {
        timerObservers.add(timerObserver)
    }

    override fun unSubscribe(timerObserver: TimerObserver) {
        timerObservers.remove(timerObserver)
    }

    override fun stopTimer() {
        countDownTimer.cancel()
    }
}
