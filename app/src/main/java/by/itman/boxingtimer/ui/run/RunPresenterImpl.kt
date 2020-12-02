package by.itman.boxingtimer.ui.run


import android.util.Log
import by.itman.boxingtimer.models.*
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.TimerPresentationImpl
import moxy.MvpPresenter
import java.time.Duration
import javax.inject.Inject
import kotlin.properties.Delegates

class  RunPresenterImpl
@Inject constructor(
    private val timerProvider: TimerProvider,
    private val timerManager: TimerManager
) : MvpPresenter<RunView>(), RunPresenter, TimerObserver {

    private val tag = "RunPresenter"
    private lateinit var runView: RunView
    private lateinit var timer: TimerModel
    private var roundCount by Delegates.notNull<Int>()
    private var currentRoundNumber by Delegates.notNull<Int>()


    override fun init(view: RunView, timerId: Int) {
        runView = view
        timerProvider.getById(timerId)?.let { timer = it }
            ?: throw ExceptionInInitializerError("TimerModel with id $timerId return null")
        roundCount = timer.roundQuantity
        currentRoundNumber = roundCount
        timerManager.subscribe(this)
    }

    override fun runTimer() {
        timerManager.run(TimerPresentationImpl(timer))
    }

    override fun onDestroy() {
        Log.i(tag, "onDestroy")
        super.onDestroy()
    }

//    override fun onTimerStart() {
//        runView.startTimer()
//    }

    override fun onTimerPause() {
        timerManager.pause()
        runView.showPause()
    }

    override fun onTimerResume() {
        timerManager.resume()
        runView.showResume()
    }

    override fun onTimerRestart() {
        timerManager.restart()
        runView.showRestart()
    }

    override fun onTimerStop() {
        timerManager.stop()
    }

    override fun onTimerFinished() {
        runView.finishTimer()
    }

    override fun onCountDownTick(time: Duration) {
        runView.setOnTickProgress(time)
    }

    override fun onRunUp(timer: TimerPresentation) {
        runView.setupRunUp(timer.getRunUp())
    }

    override fun onRestStart() {
        runView.startRest()
    }

    override fun onRoundStart(roundNumber: Int) {
        currentRoundNumber = roundNumber
        runView.startRound(roundCount, roundNumber)

    }

    override fun onPauseTimer() {

    }

    override fun onResumeTimer() {

    }

    override fun onRestartTimer() {

    }

    override fun onWarnAboutToEndRest() {

    }

    override fun onWarnAboutToEndRound() {

    }
}