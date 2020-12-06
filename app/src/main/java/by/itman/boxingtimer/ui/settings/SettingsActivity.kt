package by.itman.boxingtimer.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import by.itman.boxingtimer.R
import by.itman.boxingtimer.ui.editing.EditingActivity
import by.itman.boxingtimer.utils.MyAlertDialogs
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.data.TimerProvider
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private lateinit var data: List<TimerModel>

    @Inject
    lateinit var timerProvider: TimerProvider

    private lateinit var mListSettings: ListView
    private lateinit var mButCreateTimer: Button
    private val myDialog: MyAlertDialogs = MyAlertDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        data = timerProvider.getAll()
        mListSettings = findViewById(R.id.list_timers_settings)
        mButCreateTimer = findViewById(R.id.button_new_timer_settings)

        val adapter = SettingsAdapter(
            this, R.layout.settings_fragment, data,
            timerProvider = timerProvider
        )
        mListSettings.adapter = adapter

        mButCreateTimer.setOnClickListener {
            myDialog.alertDialogForString(
                context = this,
                title = R.string.txt_editing_timer_name,
                string = null,
                consumer = { string ->
                    val tm: TimerModel = timerProvider.save(
                        TimerModel(
                            id = null,
                            name = string,
                            roundDuration = Duration.ofSeconds(180),
                            restDuration = Duration.ofSeconds(60),
                            roundQuantity = 8,
                            runUp = Duration.ofSeconds(20),
                            noticeOfEndRound = Duration.ofSeconds(30),
                            noticeOfEndRest = Duration.ofSeconds(10),
                            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                            soundTypeOfStartRound = TimerSoundType.GONG,
                            soundTypeOfStartRest = TimerSoundType.GONG
                        )
                    )
                    timerProvider.setPositionActiveTimerForSpinner(timerProvider.getAll().size - 1)
                    startActivity(
                        Intent(applicationContext, EditingActivity::class.java)
                            .putExtra("id", tm.id)
                    )
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        data = timerProvider.getAll()
        val adapter = SettingsAdapter(
            this, R.layout.settings_fragment, data,
            timerProvider = timerProvider
        )
        mListSettings.adapter = adapter
    }
}
