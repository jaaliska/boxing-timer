package by.itman.boxingtimer.ui.main

import android.content.Intent
import by.itman.boxingtimer.models.TimerPresentation


interface MainView {

    fun updateView(timer: TimerPresentation)
    fun startActivityWith(intent: Intent)
}