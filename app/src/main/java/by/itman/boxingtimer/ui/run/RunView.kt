package by.itman.boxingtimer.ui.run

import java.time.Duration


interface RunView {
    fun setupRunUp()
    fun setOnTickProgress(progress: Duration)
    fun showPause()
    fun showRestart()
    fun showResume()
    fun startRound(roundCount: Int, roundNumber: Int, roundDuration: Duration)
    fun startRest(restDuration: Duration)
    fun warnOfEndRound()
    fun warnOfEndRest()
    fun finishTimer()
}