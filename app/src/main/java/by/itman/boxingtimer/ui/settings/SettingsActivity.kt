package by.itman.boxingtimer.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import by.itman.boxingtimer.R
import by.itman.boxingtimer.ui.editing.EditingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(), SettingsView {

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    private lateinit var mListSettings: ListView
    private lateinit var mButCreateTimer: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsPresenter.setView(this)
        setContentView(R.layout.activity_settings)
        mListSettings = findViewById(R.id.list_timers_settings)
        setAdapter()
        mButCreateTimer = findViewById(R.id.button_new_timer_settings)
        mButCreateTimer.setOnClickListener {
            settingsPresenter.onCreateNewTimer(this)
        }
    }

    override fun startEdition(timerId: Int?) {
        startActivity(
            Intent(applicationContext, EditingActivity::class.java)
                .putExtra("id", timerId)
        )
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    private fun setAdapter() {
        mListSettings.adapter = settingsPresenter.setAdapter(this, R.layout.settings_fragment)
    }
}
