package by.itman.boxingtimer.activities


import android.content.ComponentName
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import by.itman.boxingtimer.R
import by.itman.boxingtimer.presenters.RunPresenter
import by.itman.boxingtimer.utils.MyUtils
import by.itman.boxingtimer.views.RunView
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class RunActivity : AppCompatActivity(), RunView, ServiceConnection {
    @Inject
    lateinit var runPresenter: RunPresenter
    lateinit var mTxtForExample: TextView
    private val myUtils = MyUtils()
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)
        //startInstance(context = this)
        initialiseView()
        val arguments = intent.extras
        val id = arguments?.getInt("id")
        if (id != null) {
            runPresenter.init(this, id)
        }

    }

    private fun initialiseView() {
        mTxtForExample = findViewById(R.id.txt_run_round_show)
    }

    override fun setRunUpProgress(progress: Duration) {
        mTxtForExample.text = myUtils.formatDuration(progress)

    }



    override fun setupRunUp(duration: Duration) {
        TODO("Not yet implemented")
    }




    override fun startTimer() {

    }

    override fun showPause() {
        TODO("Not yet implemented")
    }

    override fun startNextQuantity() {
        TODO("Not yet implemented")
    }

    override fun startRound() {
        TODO("Not yet implemented")
    }

    override fun startRest() {
        TODO("Not yet implemented")
    }

    override fun warnBeforeRoundEnd() {
        TODO("Not yet implemented")
    }

    override fun finishTimer() {
        // TODO: 12.11.2020
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        Log.i("RunActivity", "onPause")
        super.onPause()
    }

    override fun onDestroy() {
        runPresenter.onTimerStop()
        super.onDestroy()
    }
}