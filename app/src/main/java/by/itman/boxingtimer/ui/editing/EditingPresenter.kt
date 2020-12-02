package by.itman.boxingtimer.ui.editing

import by.itman.boxingtimer.models.TimerField


interface EditingPresenter {
    fun setView(view: EditingView)
    fun setActualTimerId(id: Int)
    fun onFieldClick(timerField: TimerField)
}