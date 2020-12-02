package by.itman.boxingtimer.utils

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

    @Volatile
    private var cdTimer: CountDownTimer? = null
    private var pausedWithMillsUntilFinish: Long = millisInFuture
    private var startTimeStamp: Long = 0L

    @Volatile
    private var  state: TimerState = TimerState.PENDING
    private val tag = "AdvancedCountDownTimer"


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
                this@AdvancedCountDownTimer.onTick(millisUntilFinished)
                pausedWithMillsUntilFinish = millisUntilFinished
            }
            override fun onFinish() {
                cdTimer = null
                state = TimerState.FINISHED
                this@AdvancedCountDownTimer.onFinish()
                //wait-notify
            }

        }.start()
    }

    fun pause() {
        Log.i(tag, "pause")
        if (state != TimerState.STARTED) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        cdTimer = null
        state = TimerState.PAUSED

    }

    fun resume() {
        Log.i(tag, "resume")
        if (state != TimerState.PAUSED) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        cdTimer = null
        this.createInternalTimer(pausedWithMillsUntilFinish)
        state = TimerState.STARTED
    }

    fun restart() {
        Log.i(tag, "restart")
        if (state == TimerState.PENDING) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        cdTimer = null
        this.createInternalTimer(millisInFuture - 5) //
        state = TimerState.STARTED
    }

    fun cancel() {
        Log.i(tag, "cancel")
        cdTimer?.cancel()
        cdTimer = null
        state = TimerState.FINISHED
    }
}