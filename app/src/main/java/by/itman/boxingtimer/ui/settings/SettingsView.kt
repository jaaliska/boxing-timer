package by.itman.boxingtimer.ui.settings;

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SettingsView: MvpView {
    fun startEdition(timerId: Int?)
}
