

package by.itman.boxingtimer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import by.itman.boxingtimer.R
import by.itman.boxingtimer.adapters.MyAlertDialogs
import by.itman.boxingtimer.adapters.SettingsAdapter
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.providers.TimerProvider
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    private lateinit var data: List<TimerModel>
    @Inject
    lateinit var timerProvider: TimerProvider
    private lateinit var mListSettings: ListView
    private lateinit var mCreateTimer: Button
    private val myDialog: MyAlertDialogs = MyAlertDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        data = timerProvider.getAll()
        mListSettings = findViewById(R.id.list_timers_settings)
        mCreateTimer = findViewById(R.id.button_new_timer_settings)

        val adapter = SettingsAdapter(applicationContext, R.layout.settings_fragment, data,
            timerProvider = timerProvider)
        mListSettings.adapter = adapter

        mCreateTimer.setOnClickListener {
            myDialog.alertDialogForString(
                context = this,
                title = R.string.txt_editing_timer_name,
                string = null,
                consumer = {string ->
                    val tm: TimerModel = timerProvider.save(
                        TimerModel(
                            id = null,
                            name = string,
                            roundDuration = Duration.ofSeconds(180),
                            restDuration =  Duration.ofSeconds(60),
                            roundQuantity = 8,
                            runUp = Duration.ofSeconds(20),
                            noticeOfEndRound = Duration.ofSeconds(10),
                            noticeOfEndRest = Duration.ofSeconds(5),
                            soundTypeOfEndRestNotice = TimerSoundType.WARNING,
                            soundTypeOfEndRoundNotice = TimerSoundType.WARNING,
                            soundTypeOfStartRound = TimerSoundType.GONG,
                            soundTypeOfStartRest = TimerSoundType.GONG
                        )
                    )
                    startActivity(
                        Intent(applicationContext, EditingActivity::class.java)
                            .putExtra("id", tm.id))
                }
            )
        }
    }
}
