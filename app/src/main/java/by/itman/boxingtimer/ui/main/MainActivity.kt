package by.itman.boxingtimer.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.ApplicationDefaultTimers
import by.itman.boxingtimer.models.TimerPresentation
import by.itman.boxingtimer.ui.BaseActivity
import by.itman.boxingtimer.utils.timerFormat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity(), MainView {
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
    private lateinit var spinner: Spinner

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var applicationDefaultTimers: ApplicationDefaultTimers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.setView(this)
        applicationDefaultTimers.initializeDefaultTimers()
        initFields()
        setOnClickListeners()
        mainPresenter.setSpinner(this, spinner)
    }

    private fun initFields() {
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
        spinner = findViewById(R.id.spinner_main_timer)
    }

    private fun setOnClickListeners() {
        mImgButSettings.setOnClickListener {
            mainPresenter.onClickSettings(this)
        }

        mTimeRoundDuration.setOnClickListener {
            mainPresenter.onClickRoundDuration(this)
        }

        mTimeRestDuration.setOnClickListener {
            mainPresenter.onClickRestDuration(this)
        }

        mTimeRoundQuantity.setOnClickListener {
            mainPresenter.onClickRoundQuantity(this)
        }

        mButtonStart.setOnClickListener {
            mainPresenter.onClickStart(this)
        }
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onResumedActivity()
        mainPresenter.setSpinner(this, spinner)
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.onPausedActivity()
    }

    override fun updateView(timer: TimerPresentation) {
        mTimeRestDuration.text = timer.getRestDuration().timerFormat()
        mTimeRoundDuration.text = timer.getRoundDuration().timerFormat()
        mTimeRoundQuantity.text = "${timer.getRoundQuantity()}"
        mTrainingDuration.text = getString(R.string.main_txt_training_duration, timer.getTrainingDuration())
    }

    override fun startActivityWith(intent: Intent) {
        startActivity(intent)
    }
}