package by.itman.boxingtimer.ui.run

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import by.itman.boxingtimer.models.TimerPresentation
import by.itman.boxingtimer.models.TimerSoundType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import javax.inject.Inject


class SoundNoticePlayback @Inject constructor(
    @ApplicationContext val context: Context, private val timerManager: TimerManager
    ) : TimerObserver {

    private lateinit var actualTimer: TimerPresentation
    private var mPlayer: MediaPlayer? = null
    private val tag = "SoundNoticePlayback"

    private fun play(sound: TimerSoundType) {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(sound))
        mPlayer!!.start()
    }

    override fun onCountDownTick(time: Duration) {

    }

    override fun onRunUp(timer: TimerPresentation) {
        actualTimer = timer
        Log.i(tag, "onRunUp")
    }

    override fun onRoundStart(roundNumber: Int) {
        play(actualTimer.getSoundTypeOfStartRound())
        Log.i(tag, "onRoundStart")
    }

    override fun onRestStart() {
        play(actualTimer.getSoundTypeOfStartRest())
        Log.i(tag, "onRestStart")
    }

    override fun onPauseTimer() {
    }

    override fun onResumeTimer() {
    }

    override fun onRestartTimer() {
    }

    override fun onNoticeOfEndRest() {
        play(actualTimer.getSoundTypeOfEndRestNotice())
        Log.i(tag, "onWarnAboutToEndRest")
    }

    override fun onNoticeOfEndRound() {
        play(actualTimer.getSoundTypeOfEndRoundNotice())
        Log.i(tag, "onWarnAboutToEndRound")
    }

    override fun onTimerFinished() {
        play(actualTimer.getSoundTypeOfStartRest())
        Log.i(tag, "onTimerFinished")
        timerManager.unSubscribe(this)
    }
}