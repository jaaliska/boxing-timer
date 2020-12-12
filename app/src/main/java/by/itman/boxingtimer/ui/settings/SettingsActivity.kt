package by.itman.boxingtimer.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.widget.SwitchCompat
import by.itman.boxingtimer.R
import by.itman.boxingtimer.ui.BaseActivity
import by.itman.boxingtimer.ui.editing.EditingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity: BaseActivity(), SettingsView {

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    private lateinit var mListSettings: ListView
    private lateinit var mButCreateTimer: Button
    private lateinit var mSwitchOfDarkTheme: SwitchCompat

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
        changeTheme()
    }

    private fun changeTheme() {
        mSwitchOfDarkTheme = findViewById(R.id.switch_settings_darkTheme)
        mSwitchOfDarkTheme.isChecked = appPreferences.isDarkTheme()
        mSwitchOfDarkTheme.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                appPreferences.setDarkTheme(true)
                val intent = intent
                finish()
                startActivity(intent)
            } else {
                appPreferences.setDarkTheme(false)
                val intent = intent
                finish()
                startActivity(intent)
            }
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
