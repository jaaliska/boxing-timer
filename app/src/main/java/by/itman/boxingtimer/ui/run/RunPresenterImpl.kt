package by.itman.boxingtimer.ui.run


import android.content.Context
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.*
import by.itman.boxingtimer.service.TimerService
import by.itman.boxingtimer.utils.MyAlertDialogs
import java.time.Duration
import javax.inject.Inject
import kotlin.properties.Delegates


class RunPresenterImpl
@Inject constructor(
    private val timerProvider: TimerProvider,
    private val timerService: TimerService,
    private val soundNoticePlayback: SoundNoticePlayback
) : RunPresenter {

    private val tag = "RunPresenter"
    private val myDialog = MyAlertDialogs()
    private lateinit var runView: RunView
    private lateinit var actualTimer: TimerModel
    private var roundCount by Delegates.notNull<Int>()
    private var currentRoundNumber by Delegates.notNull<Int>()


    override fun init(view: RunView, timerId: Int) {
        runView = view
        timerProvider.getById(timerId)?.let { actualTimer = it }
            ?: throw ExceptionInInitializerError("TimerModel with id $timerId return null")
        roundCount = actualTimer.roundQuantity
        currentRoundNumber = roundCount
        timerService.subscribe(TimerObserverForPresenter())
    }

    override fun runTimer() {
        timerService.subscribe(soundNoticePlayback)
        timerService.run(TimerPresentationImpl(actualTimer))
    }

    override fun onTimerPause() {
        timerService.pause()
    }

    override fun onTimerResume() {
        timerService.resume()
    }

    override fun onTimerRestart() {
        timerService.restart()
    }

    override fun onTimerFinished() {
        timerService.finish()
    }

    override fun onExitByBackButton(context: Context) {
        myDialog.alertDialogForActionVerification(context = context,
            title = R.string.txt_run_dialog_exit,
            consumer = {
                runView.finishTimer()
                timerService.stop()
            }
        )
    }

    inner class TimerObserverForPresenter : TimerObserver {

        override fun onCountDownTick(time: Duration) {
            runView.setOnTickProgress(time)
        }

        override fun onRunUp(timer: TimerPresentation) {
            runView.setupRunUp(actualTimer.runUp)
        }

        override fun onRoundStart(roundNumber: Int) {
            currentRoundNumber = roundNumber
            runView.startRound(roundCount, roundNumber, actualTimer.roundDuration)
        }

        override fun onRestStart() {
            runView.startRest(actualTimer.restDuration)
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

        override fun onTimerStopped() {
            runView.stopTraining()
        }
    }
}