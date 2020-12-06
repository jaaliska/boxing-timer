package by.itman.boxingtimer.ui.settings

import android.content.Context
import android.widget.ListAdapter

interface SettingsPresenter {

    fun setAdapter(context: Context, resource: Int): ListAdapter
    fun setView(view: SettingsView)
    fun onCreateNewTimer(context: Context)
}