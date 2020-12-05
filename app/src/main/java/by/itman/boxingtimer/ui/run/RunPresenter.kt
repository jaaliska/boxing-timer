package by.itman.boxingtimer.ui.run;

import android.content.Context

interface RunPresenter {
    fun init(view: RunView, timerId: Int)
    fun runTimer()
    fun onTimerPause()
    fun onTimerResume()
    fun onTimerRestart()
    fun onTimerFinished()
    fun onExitByBackButton(context: Context)
}
