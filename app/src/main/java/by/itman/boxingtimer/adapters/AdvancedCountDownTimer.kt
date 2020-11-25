package by.itman.boxingtimer.adapters

import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log

abstract class AdvancedCountDownTimer(
    var millisInFuture: Long,
    var countDownInterval: Long,
    var intervalDivider: Int = 1

) {
    private enum class TimerState {
        PENDING, STARTED, PAUSED, FINISHED
    }

    init {
        if (intervalDivider < 1) {
            throw ExceptionInInitializerError("IntervalDivider can't be less than 1")
        }
    }

    private var cdTimer: CountDownTimer? = null
    private var pausedWithMillsUntilFinish: Long = millisInFuture
    private var nextTickOn: Long = millisInFuture
    private var startTimeStamp: Long = 0L
    private var state: TimerState = TimerState.PENDING


    abstract fun onTick(millisUntilFinished: Long)

    abstract fun onFinish()

    fun start(): AdvancedCountDownTimer {
        if (state != TimerState.PENDING) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        nextTickOn = millisInFuture - countDownInterval
        createInternalTimer(millisInFuture)
        return this
    }

    private fun createInternalTimer(millisUntilFinished: Long) {
        startTimeStamp = SystemClock.elapsedRealtime()
        state = TimerState.STARTED
        cdTimer = object: CountDownTimer(
            millisUntilFinished,
            countDownInterval / intervalDivider
        ) {
            override fun onTick(millisUntilFinished: Long) {
               // Log.i("inner timer going", millisUntilFinished.toString())
                if (millisUntilFinished <= nextTickOn) {
                    this@AdvancedCountDownTimer.onTick(millisUntilFinished)
                    nextTickOn -= countDownInterval
                }
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
    }

    fun restart() {
        if (state == TimerState.PENDING) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer!!.cancel()
        cdTimer = null
        nextTickOn = millisInFuture - countDownInterval
        this.createInternalTimer(millisInFuture)
    }

    fun cancel() {
        if (state != TimerState.STARTED && state != TimerState.PAUSED) {
            throw IllegalStateException("Timer is in state $state")
        }
        cdTimer?.cancel()
        cdTimer = null
        state = TimerState.FINISHED
    }
}