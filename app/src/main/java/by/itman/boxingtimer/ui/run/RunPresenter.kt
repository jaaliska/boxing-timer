package by.itman.boxingtimer.ui.run;

interface RunPresenter {
    fun init(view: RunView, timerId: Int)
    fun runTimer()
    fun onTimerPause()
    fun onTimerResume()
    fun onTimerRestart()
    fun onTimerFinished()
}
