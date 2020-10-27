package by.itman.boxingtimer.presenters

import android.content.Context
import by.itman.boxingtimer.views.EditingView
import java.time.Duration


interface EditingPresenter {
    fun setView(view: EditingView)
    fun setActualTimerId(id: Int)
    fun onFieldClick(timerField: TimerField)
}