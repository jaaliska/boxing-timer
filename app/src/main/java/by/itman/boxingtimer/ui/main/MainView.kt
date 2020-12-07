package by.itman.boxingtimer.ui.main

import android.content.Intent
import by.itman.boxingtimer.models.TimerPresentation
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView: MvpView {

    fun updateView(timer: TimerPresentation)
    fun startActivityWith(intent: Intent)
}