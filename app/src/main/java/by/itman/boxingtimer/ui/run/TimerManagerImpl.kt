package by.itman.boxingtimer.ui.run

import android.content.Context
import android.os.SystemClock
import android.util.Log
import by.itman.boxingtimer.utils.AdvancedCountDownTimer
import by.itman.boxingtimer.models.TimerPresentation
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import javax.inject.Inject
import kotlin.properties.Delegates

class TimerManagerImpl @Inject constructor(@ApplicationContext val context: Context) :
    TimerManager {

    private val tag = "TimerManager"
    private lateinit var countDownTimer: AdvancedCountDownTimer
    private val timerObservers = mutableListOf<TimerObserver>()
    private lateinit var actualTimer: TimerPresentation
    private var timerState = TimerState.RUN_UP
    private var isTimerPaused: Boolean = false
    private var roundCount by Delegates.notNull<Int>()
    private var currentRoundNumber = 1
    val soundNoticePlayback: SoundNoticePlayback = SoundNoticePlayback(context, this)


    override fun run(timer: TimerPresentation) {
        actualTimer = timer
        roundCount = actualTimer.getRoundQuantity()
        Log.i("timer run", timer.toString())
        startRunUp()
    }

    fun nextPlease() {
        if (currentRoundNumber < roundCount) {
            if (timerState != TimerState.ROUND) {
                startRound()
            } else if (timerState != TimerState.REST) {
                startRest()
                currentRoundNumber += 1
            }

        } else if (currentRoundNumber == roundCount) {
            startRound()
            currentRoundNumber += 1
        } else {
            timerObservers.forEach { observer -> observer.onTimerFinished() }
            currentRoundNumber = 1
        }
    }

    private fun startRunUp() {
        timerState = TimerState.REST
        timerObservers.forEach { observer -> observer.onRunUp(timer = actualTimer) }
        countDownTimer = startTimer(actualTimer.getRunUp(), null)
        Log.i(tag, "timer runUp")

    }

    private fun startRound() {
        timerState = TimerState.ROUND
        timerObservers.forEach { observer ->
            observer.onRoundStart(roundNumber = currentRoundNumber)
        }
        val noticeTime = (actualTimer.getNoticeOfEndRound().seconds).toInt()
        val noticeOfEndInSec: Int? = if (noticeTime == 0) null else noticeTime
        countDownTimer = startTimer(
            actualTimer.getRoundDuration(), noticeOfEndInSec)
        Log.i(tag, "timer startRound")
    }

    private fun startRest() {
        timerState = TimerState.REST
        timerObservers.forEach { observer -> observer.onRestStart() }
        val noticeTime = (actualTimer.getNoticeOfEndRound().seconds).toInt()
        val noticeOfEndInSec: Int? = if (noticeTime == 0) null else noticeTime
        countDownTimer = startTimer(
            actualTimer.getRestDuration(),
            noticeOfEndInSec
        )
        Log.i(tag, "timer startRest")
    }

    private fun startTimer(millis: Duration, noticeOfEndInSec: Int?): AdvancedCountDownTimer {
        val t = SystemClock.elapsedRealtime()
        countDownTimer = object : AdvancedCountDownTimer(millis.toMillis() + 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished < 1000) {
                    onFinish()
                    this.cancel()
                }
                if ((millisUntilFinished/1000).toInt() == noticeOfEndInSec) startSoundNotice()
                timerObservers.forEach { observer ->
                    observer.onCountDownTick(Duration.ofMillis(millisUntilFinished))
                }
                Log.i("$tag timer going", millisUntilFinished.toString())
            }

            override fun onFinish() {
                nextPlease()
                Log.i("$tag timer finish", (SystemClock.elapsedRealtime() - t).toString())
            }
        }.start()
        return countDownTimer
    }

    fun startSoundNotice() {
        if (timerState == TimerState.ROUND) {
            timerObservers.forEach { observer -> observer.onWarnAboutToEndRound() }
        }
        if (timerState == TimerState.REST) {
            timerObservers.forEach { observer -> observer.onWarnAboutToEndRest() }
        }
    }


    override fun pause() {
        isTimerPaused = true
        countDownTimer.pause()
    }

    override fun resume() {
        isTimerPaused = false
        countDownTimer.resume()
    }

    override fun restart() {
        isTimerPaused = false
        countDownTimer.restart()
    }

    override fun subscribe(timerObserver: TimerObserver) {
        timerObservers.add(timerObserver)
    }

    override fun unSubscribe(timerObserver: TimerObserver) {
        timerObservers.remove(timerObserver)
    }


    override fun stop() {
        countDownTimer.cancel()
        currentRoundNumber = 1
    }

    override fun getState(roundState: String, roundCount: Int, roundNumber: Int) {
        TODO("Not yet implemented")
    }
}
