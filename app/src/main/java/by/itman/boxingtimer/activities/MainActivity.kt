package by.itman.boxingtimer.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import by.itman.boxingtimer.R
import by.itman.boxingtimer.adapters.MyAlertDialogs
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.utils.MyUtils
import by.itman.boxingtimer.views.MainView
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

    private lateinit var currentModelTimer: TimerModel
    private lateinit var activeTimerModel: TimerModel

    @Inject
    lateinit var timerProvider: TimerProvider

    @Inject
    lateinit var sharedPrefs: SharedPreferences
    private lateinit var spinner: Spinner
    private lateinit var mDialogs: MyAlertDialogs
    private val myUtils: MyUtils = MyUtils()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeDefaultPrefs()
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

        val timersModel = timerProvider.getAll()
        activeTimerModel = if (timersModel.isNotEmpty()) {
            getActiveTimerModel()
        } else {
            TimerModel(
                id = 0, name = "Бокс",
                roundDuration = Duration.ofSeconds(180),
                restDuration = Duration.ofSeconds(60),
                roundQuantity = 8,
                runUp = Duration.ofSeconds(20),
                noticeOfEndRound = Duration.ofSeconds(10),
                soundType = TimerSoundType.GONG
            )
        }
        currentModelTimer = activeTimerModel.copy()

        mImgButSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

        mDialogs = MyAlertDialogs()
        mTimeRoundDuration.setOnClickListener {
            mDialogs.alertDialogForDuration(
                context = this,
                title = R.string.txt_title_name_roundDuration,
                time = currentModelTimer.roundDuration,
                consumer = { duration ->
                    mTimeRoundDuration.text = myUtils.formatDuration(duration = duration)
                    currentModelTimer.roundDuration = duration
                    updateState()
                }
            )

        }

        mTimeRestDuration.setOnClickListener {
            mDialogs.alertDialogForDuration(
                context = this,
                title = R.string.txt_title_name_restDuration,
                time = currentModelTimer.restDuration,
                consumer = { duration ->
                    mTimeRestDuration.text = myUtils.formatDuration(duration = duration)
                    currentModelTimer.restDuration = duration
                    updateState()
                }
            )

        }

        mTimeRoundQuantity.setOnClickListener {
            mDialogs.alertDialogForNumber(
                context = this,
                title = R.string.txt_title_name_roundQuantity,
                value = currentModelTimer.roundQuantity,
                consumer = { Int ->
                    mTimeRoundQuantity.text = Int.toString()
                    currentModelTimer.roundQuantity = Int
                    updateState()
                }
            )

        }

        mButtonStart.setOnClickListener {
            //TODO переход на RunActivity
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        activeTimerModel = getActiveTimerModel()
        currentModelTimer = activeTimerModel.copy()
        setSpinner()
        updateState()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        timerProvider.save(currentModelTimer)
        currentModelTimer.id?.let { setActiveTimerModel(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setSpinner() {
        val data: List<TimerModel> = timerProvider.getAll()
        val adapter: ArrayAdapter<TimerModel> = ArrayAdapter(this, R.layout.spinner_item, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner = findViewById(R.id.spinner_main_timer)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentModelTimer = parent?.getItemAtPosition(position) as TimerModel
                updateState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateState() {

        mTimeRestDuration.text = myUtils.formatDuration(currentModelTimer.restDuration)
        mTimeRoundDuration.text = myUtils.formatDuration(currentModelTimer.roundDuration)
        mTimeRoundQuantity.text = "${currentModelTimer.roundQuantity}"
        mTrainingDuration.text = StringBuilder(
            "Длительность тренировки: " +
                    "${currentModelTimer.getTrainingDuration()} мин."
        )
    }

    private fun setActiveTimerModel(id: Int) {
        sharedPrefs.edit().putInt("active_timer_model", id).apply()
    }

    private fun getActiveTimerModel(): TimerModel {
        val id = sharedPrefs.getInt("active_timer_model", 0)
        return if (id == 0 || timerProvider.getById(id) == null) {
            timerProvider.getAll()[0]
        } else {
            timerProvider.getById(id)!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeDefaultPrefs() {
        if (!sharedPrefs.contains("defaults_initialized")) {
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Бокс",
                    roundDuration = Duration.ofSeconds(180),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(10),
                    soundType = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Лёгкий бокс",
                    roundDuration = Duration.ofSeconds(120),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(10),
                    soundType = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "ММА",
                    roundDuration = Duration.ofSeconds(300),
                    restDuration = Duration.ofSeconds(60),
                    roundQuantity = 5,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(10),
                    soundType = TimerSoundType.GONG
                )
            )
            timerProvider.save(
                TimerModel(
                    id = null,
                    name = "Табата",
                    roundDuration = Duration.ofSeconds(20),
                    restDuration = Duration.ofSeconds(10),
                    roundQuantity = 8,
                    runUp = Duration.ofSeconds(20),
                    noticeOfEndRound = Duration.ofSeconds(0),
                    soundType = TimerSoundType.GONG
                )
            )
            sharedPrefs.edit().putBoolean("defaults_initialized", true).apply()
            setActiveTimerModel(1)
        }
    }
}