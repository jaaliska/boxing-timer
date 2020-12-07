package by.itman.boxingtimer.ui.main

import android.content.Context
import android.widget.Spinner

interface MainPresenter {
    fun setView(view: MainView)
    fun setSpinner(context: Context, spinner: Spinner)
    fun onClickRoundDuration(context: Context)
    fun onClickRestDuration(context: Context)
    fun onClickRoundQuantity(context: Context)
    fun onClickStart(context: Context)
    fun onClickSettings(context: Context)
    fun onPausedActivity()
    fun onResumedActivity()
}