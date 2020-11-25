package by.itman.boxingtimer.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.time.Duration

@StateStrategyType(AddToEndSingleStrategy::class)
interface RunView: MvpView {
    fun setupRunUp(duration: Duration)
    fun setRunUpProgress(progress: Duration)
    fun startTimer()
    fun showPause()
    fun startNextQuantity()
    fun startRound()
    fun startRest()
    fun warnBeforeRoundEnd()
    fun finishTimer()
}