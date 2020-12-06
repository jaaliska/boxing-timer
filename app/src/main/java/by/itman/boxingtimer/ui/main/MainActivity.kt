package by.itman.boxingtimer.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import by.itman.boxingtimer.R
import by.itman.boxingtimer.ui.run.RunActivity
import by.itman.boxingtimer.ui.settings.SettingsActivity
import by.itman.boxingtimer.utils.MyAlertDialogs
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.utils.timerFormat
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), MainView {
    private lateinit var mImgButSettings: ImageButton
    private lateinit var mTimeRoundDuration: TextView
    private lateinit var mTxtRoundDuration: TextView
    private lateinit var mTimeRestDuration: TextView
    private lateinit var mTxtRestDuration: TextView
    private lateinit var mTxtRoundQuantity: TextView
    private lateinit var mTimeRoundQuantity: TextView
    private lateinit var mTrainingDuration: TextView
    private lateinit var mButtonStart: Button
    private lateinit var mTxtChangeTimer: TextView

    private lateinit var activeTimerModel: TimerModel
    private var positionCurrentModelTimerInSpinner: Int = 0

    @Inject
    lateinit var timerProvider: TimerProvider

    @Inject
    lateinit var sharedPrefs: SharedPreferences
    private lateinit var spinner: Spinner
    private lateinit var mDialogs: MyAlertDialogs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerProvider.initializeDefaultTimers()
        mImgButSettings = findViewById(R.id.imgBut_main_settings)
        mTxtRoundDuration = findViewById(R.id.txt_main_roundDuration)
        mTimeRoundDuration = findViewById(R.id.time_main_roundDuration)
        mTxtRestDuration = findViewById(R.id.txt_main_restDuration)
        mTimeRestDuration = findViewById(R.id.time_main_restDuration)
        mTxtRoundQuantity = findViewById(R.id.txt_main_roundQuantity)
        mTimeRoundQuantity = findViewById(R.id.time_main_roundQuantity)
        mTrainingDuration = findViewById(R.id.txt_main_training_duration)
        mButtonStart = findViewById(R.id.button_main_start)
        mTxtChangeTimer = findViewById(R.id.txt_main_change_timer)

        activeTimerModel = getActiveTimerModel()

        mImgButSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

        mDialogs = MyAlertDialogs()
        mTimeRoundDuration.setOnClickListener {
            mDialogs.alertDialogForDuration(
                context = this,
                title = R.string.txt_title_name_roundDuration,
                time = activeTimerModel.roundDuration,
                consumer = { duration ->
                    mTimeRoundDuration.text = duration.timerFormat()
                    activeTimerModel.roundDuration = duration
                    timerProvider.save(activeTimerModel)
                    updateState()
                }
            )
        }

        mTimeRestDuration.setOnClickListener {
            mDialogs.alertDialogForDuration(
                context = this,
                title = R.string.txt_title_name_restDuration,
                time = activeTimerModel.restDuration,
                consumer = { duration ->
                    mTimeRestDuration.text = duration.timerFormat()
                    activeTimerModel.restDuration = duration
                    updateState()
                }
            )
        }

        mTimeRoundQuantity.setOnClickListener {
            mDialogs.alertDialogForNumber(
                context = this,
                title = R.string.txt_title_name_roundQuantity,
                value = activeTimerModel.roundQuantity,
                consumer = { Int ->
                    mTimeRoundQuantity.text = Int.toString()
                    activeTimerModel.roundQuantity = Int
                    updateState()
                }
            )
        }

        mButtonStart.setOnClickListener {
            startActivity(Intent(applicationContext, RunActivity::class.java)
                    .putExtra("id", activeTimerModel.id))
        }
    }

    override fun onResume() {
        super.onResume()
        activeTimerModel = getActiveTimerModel()
        setSpinner()
        updateState()
    }

    override fun onPause() {
        super.onPause()
        timerProvider.save(activeTimerModel)
        activeTimerModel.id?.let { setActiveTimerModel(it) }
    }

    private fun setSpinner() {
        val data: List<TimerModel> = timerProvider.getAll()
        val adapter: ArrayAdapter<TimerModel> = ArrayAdapter(this, R.layout.spinner_item, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner = findViewById(R.id.spinner_main_timer)
        spinner.adapter = adapter
        spinner.setSelection(positionCurrentModelTimerInSpinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                activeTimerModel = parent?.getItemAtPosition(position) as TimerModel
                positionCurrentModelTimerInSpinner = position
                updateState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateState() {
        mTimeRestDuration.text = activeTimerModel.restDuration.timerFormat()
        mTimeRoundDuration.text = activeTimerModel.roundDuration.timerFormat()
        mTimeRoundQuantity.text = "${activeTimerModel.roundQuantity}"
        mTrainingDuration.text = StringBuilder(
            "Длительность тренировки: " +
                    "${activeTimerModel.getTrainingDuration()} мин."
        )
    }

    private fun setActiveTimerModel(id: Int) {
        timerProvider.setActiveTimer(id)
        timerProvider.setPositionActiveTimerForSpinner(positionCurrentModelTimerInSpinner)
    }

    private fun getActiveTimerModel(): TimerModel {
        val timerPositionInSpinner = timerProvider.getPositionActiveTimerForSpinner()
        positionCurrentModelTimerInSpinner =
            if (timerPositionInSpinner >= timerProvider.getAll().size) 0 else timerPositionInSpinner

        val timersModel = timerProvider.getAll()
        return  if (timersModel.isNotEmpty()) {
            timerProvider.getActiveTimer()
        } else {
            TimerModel(
                id = null, name = "Бокс",
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
        }
    }
}