package by.itman.boxingtimer.ui.run


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.*
import by.itman.boxingtimer.utils.MyAlertDialogs
import moxy.MvpPresenter
import java.time.Duration
import javax.inject.Inject
import kotlin.properties.Delegates


class RunPresenterImpl
@Inject constructor(
    private val timerProvider: TimerProvider,
    private val timerManager: TimerManager
) : MvpPresenter<RunView>(), RunPresenter {

    private val tag = "RunPresenter"
    private val myDialog = MyAlertDialogs()
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
        timerManager.subscribe(TimerObserverForPresenter())
    }

    override fun runTimer() {
        timerManager.run(TimerPresentationImpl(timer))
    }

    override fun onTimerPause() {
        timerManager.pause()
    }

    override fun onTimerResume() {
        timerManager.resume()
    }

    override fun onTimerRestart() {
        timerManager.restart()
    }

    override fun onTimerFinished() {
        timerManager.finish()
    }

    override fun onExitByBackButton(context: Context) {
        myDialog.alertDialogForActionVerification(context = context,
            title = R.string.txt_run_dialog_exit,
            consumer = { runView.finishTimer() }
        )
    }

    override fun onDestroy() {
        Log.i(tag, "onDestroy")
        super.onDestroy()
    }

    inner class TimerObserverForPresenter : TimerObserver {
        override fun onCountDownTick(time: Duration) {
            runView.setOnTickProgress(time)
        }

        override fun onRunUp(timer: TimerPresentation) {
            runView.setupRunUp(timer.getRunUp())
        }

        override fun onRoundStart(roundNumber: Int) {
            currentRoundNumber = roundNumber
            runView.startRound(roundCount, roundNumber)
        }

        override fun onRestStart() {
            runView.startRest()
        }

        override fun onPauseTimer() {
            runView.showPause()
        }

        override fun onResumeTimer() {
            runView.showResume()
        }

        override fun onRestartTimer() {
            runView.showRestart()
        }

        override fun onNoticeOfEndRest() {
            runView.warnOfEndRest()
        }

        override fun onNoticeOfEndRound() {
            runView.warnOfEndRound()
        }

        override fun onTimerFinished() {
            runView.finishTimer()
        }
    }
}