package by.itman.boxingtimer.adapters

import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log

abstract class AdvancedCountDownTimer(
    var millisInFuture: Long,
    var countDownInterval: Long
   ) {
    private enum class TimerState {
        PENDING, STARTED, PAUSED, FINISHED
    }


    private var cdTimer: CountDownTimer? = null
    private var pausedWithMillsUntilFinish: Long = millisInFuture
    private var startTimeStamp: Long = 0L
    private var state: TimerState = TimerState.PENDING


    abstract fun onTick(millisUntilFinished: Long)

    abstract fun onFinish()

    fun start(): AdvancedCountDownTimer {
        if (state != TimerState.PENDING) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        createInternalTimer(millisInFuture)
        return this
    }

    private fun createInternalTimer(millisUntilFinished: Long) {
        startTimeStamp = SystemClock.elapsedRealtime()
        state = TimerState.STARTED
        cdTimer = object: CountDownTimer(
            millisUntilFinished,
            countDownInterval
        ) {
            override fun onTick(millisUntilFinished: Long) {
               // Log.i("inner timer going", millisUntilFinished.toString())
                this@AdvancedCountDownTimer.onTick(millisUntilFinished)

            }
            override fun onFinish() {
                cdTimer = null
                state = TimerState.FINISHED
                this@AdvancedCountDownTimer.onFinish()
            }

        }.start()
    }

    fun pause() {
        if (state != TimerState.STARTED) {
            throw IllegalStateException("Timer is in state $state")
        }
        pausedWithMillsUntilFinish = SystemClock.elapsedRealtime() - startTimeStamp
        cdTimer!!.cancel()
        cdTimer = null
        state = TimerState.PAUSED

    }

    fun resume() {
        if (state != TimerState.PAUSED) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer!!.cancel()
        cdTimer = null
        this.createInternalTimer(pausedWithMillsUntilFinish)
        state = TimerState.STARTED
    }

    fun restart() {
        if (state == TimerState.PENDING) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer!!.cancel()
        cdTimer = null
        this.createInternalTimer(millisInFuture)
        state = TimerState.STARTED
    }

    fun cancel() {
        cdTimer?.cancel()
        cdTimer = null
        state = TimerState.FINISHED
    }
}