package by.itman.boxingtimer.presenters


import by.itman.boxingtimer.adapters.TimerManager
import by.itman.boxingtimer.models.*
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.views.RunView
import by.itman.boxingtimer.views.TimerPresentation
import moxy.MvpPresenter
import java.time.Duration
import javax.inject.Inject

class  RunPresenterImpl
@Inject constructor(
    private val timerProvider: TimerProvider,
    private val timerManager: TimerManager
) : MvpPresenter<RunView>(), RunPresenter, TimerObserver {

    private lateinit var runView: RunView
    private lateinit var timer: TimerModel


    override fun init(view: RunView, timerId: Int) {
        runView = view
        timerProvider.getById(timerId)?.let { timer = it }
        timerManager.subscribe(this)
        timerManager.setTimer(timer)
        timerManager.startTimer()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTimerStart() {
        runView.startTimer()
    }

    override fun onTimerPause() {
        TODO("Not yet implemented")
    }

    override fun onTimerStop() {
        timerManager.stopTimer()
    }

    override fun onTimerFinished() {

    }

    override fun onTick(time: Duration) {
        runView.setRunUpProgress(time)
    }

    override fun getTimer(): TimerPresentation {
        return TimerPresentationImpl(timer)
    }

}