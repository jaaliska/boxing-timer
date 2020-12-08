package by.itman.boxingtimer.ui.run


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.itman.boxingtimer.R
import by.itman.boxingtimer.utils.timerFormat
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import javax.inject.Inject


@AndroidEntryPoint
class RunActivity : AppCompatActivity(), RunView { // ServiceConnection
    @Inject
    lateinit var runPresenter: RunPresenter
    private lateinit var mTxtCurrentRound: TextView
    private lateinit var mTxtTimerStatus: TextView
    private lateinit var mTxtContentTimer: View
    private lateinit var mTxtCountTime: TextView
    private lateinit var mButRestart: ImageButton
    private lateinit var mButPause: ImageButton
    private lateinit var mButResume: ImageButton
    private var timerState: TimerState =  TimerState.RUN_UP
    private var isTimerPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)
        initialiseView()
        val arguments = intent.extras
        val id = arguments?.getInt("id")
            ?: throw ExceptionInInitializerError("Id of ModelTimer can't be null")
        runPresenter.init(this, id)
        runPresenter.runTimer()
    }

    private fun initialiseView() {
        mTxtCurrentRound = findViewById(R.id.txt_run_round_show)
        mTxtTimerStatus = findViewById(R.id.txt_run_timer_status)
        mTxtContentTimer = findViewById(R.id.include_content_timer)
        mTxtCountTime = mTxtContentTimer.findViewById(R.id.txt_run_count_time)
        mButPause = findViewById(R.id.button_run_pause)
        mButRestart = findViewById(R.id.button_run_restart)
        mButResume = findViewById(R.id.button_run_resume)
        mButPause.setOnClickListener{
            runPresenter.onTimerPause()
        }
        mButRestart.setOnClickListener{
            runPresenter.onTimerRestart()
        }
        mButResume.setOnClickListener{
            runPresenter.onTimerResume()
        }
        updateButtons()
    }

    override fun setOnTickProgress(progress: Duration) {
        mTxtCountTime.text = progress.timerFormat()
    }

    override fun setupRunUp() {
        mTxtTimerStatus.text = getString(R.string.run_txt_title_runUp)
    }

    private fun updateButtons() {
        if (isTimerPaused) {
            mButPause.isEnabled = false
            mButResume.isEnabled = true
            mButPause.visibility = View.GONE
            mButResume.visibility = View.VISIBLE
        }
        if (!isTimerPaused) {
            mButPause.isEnabled = true
            mButResume.isEnabled = false
            mButPause.visibility = View.VISIBLE
            mButResume.visibility = View.GONE
        }
    }

    override fun showPause() {
        isTimerPaused = true
        updateButtons()
    }

    override fun showRestart() {
        isTimerPaused = false
        updateButtons()
    }

    override fun showResume() {
        isTimerPaused = false
        updateButtons()
    }

    override fun startRound(roundCount: Int, roundNumber: Int) {
        timerState = TimerState.ROUND
        mTxtCurrentRound.text = getString(
            R.string.run_txt_title_round_tracking, roundNumber, roundCount
        )
        mTxtTimerStatus.text = getString(R.string.run_txt_title_start_round)
    }

    override fun startRest() {
        timerState = TimerState.REST
        mTxtTimerStatus.text = getString(R.string.run_txt_title_start_rest)
    }

    override fun warnOfEndRound() {}

    override fun warnOfEndRest() {}

    override fun finishTimer() {
        finish()
    }


    override fun onPause() {
        Log.i("RunActivity", "onPause")
        super.onPause()
    }

    override fun onBackPressed() {
        runPresenter.onExitByBackButton(context = this)
    }

    override fun onDestroy() {
        runPresenter.onTimerFinished()
        Log.i("RunActivity", "onDestroy")
        super.onDestroy()
    }
}