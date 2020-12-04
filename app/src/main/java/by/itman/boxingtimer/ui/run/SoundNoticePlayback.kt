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
    @ApplicationContext val context: Context, timerManager: TimerManager
    ) : TimerObserver {

    private lateinit var actualTimer: TimerPresentation
    private var mPlayer: MediaPlayer? = null
    private val tag = "SoundNoticePlayback"

    init {
        timerManager.subscribe(this)
    }

    override fun onCountDownTick(time: Duration) {

    }

    override fun onRunUp(timer: TimerPresentation) {
        actualTimer = timer
        Log.i(tag, "onRunUp")
    }

    override fun onRoundStart(roundNumber: Int) {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(actualTimer.getSoundTypeOfStartRound()))
        mPlayer!!.start()
        Log.i(tag, "onRoundStart")
    }

    override fun onRestStart() {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(actualTimer.getSoundTypeOfStartRest()))
        mPlayer!!.start()
        Log.i(tag, "onRestStart")
    }

    override fun onPauseTimer() {
    }

    override fun onResumeTimer() {
    }

    override fun onRestartTimer() {
    }

    override fun onNoticeOfEndRest() {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(actualTimer.getSoundTypeOfEndRestNotice()))
        mPlayer!!.start()
        Log.i(tag, "onWarnAboutToEndRest")
    }

    override fun onNoticeOfEndRound() {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(actualTimer.getSoundTypeOfEndRoundNotice()))
        mPlayer!!.start()
        Log.i(tag, "onWarnAboutToEndRound")
    }

    override fun onTimerFinished() {
        mPlayer?.release()
        mPlayer = MediaPlayer.create(context,
            TimerSoundType.getResource(actualTimer.getSoundTypeOfStartRest()))
        mPlayer!!.start()
        Log.i(tag, "onTimerFinished")
    }
}