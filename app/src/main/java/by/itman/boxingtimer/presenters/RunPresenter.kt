package by.itman.boxingtimer.presenters;

import by.itman.boxingtimer.views.RunView

interface RunPresenter {
    fun init(view: RunView, timerId: Int)
    fun onTimerStart()
    fun onTimerPause()
    fun onTimerStop()
    fun onTimerFinished()
}
