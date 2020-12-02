package by.itman.boxingtimer.ui.run

import android.content.Context
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.time.Duration

@StateStrategyType(AddToEndSingleStrategy::class)
interface RunView: MvpView {
    fun setupRunUp(duration: Duration)
    fun setOnTickProgress(progress: Duration)
 //   fun startTimer()
    fun showPause()
    fun showRestart()
    fun showResume()
    fun startRound(roundCount: Int, roundNumber: Int)
    fun startRest()
    fun warnBeforeRoundEnd()
    fun finishTimer()
}