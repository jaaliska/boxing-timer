package by.itman.boxingtimer.models

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.annotation.RequiresApi
import by.itman.boxingtimer.presenters.TimerPresentationImpl
import java.time.Duration
import javax.inject.Inject


var serviceInstance: TimerServiceImpl? = null

fun startInstance(context: Context) {
    if (serviceInstance == null) {
        context.startService(Intent(context, TimerServiceImpl::class.java))
    }
}

val timerObservers = mutableListOf<TimerObserver>()

class TimerServiceImpl: Service(), TimerService {
//  interface, strategy, timerManager

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timer: TimerPresentationImpl


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceInstance = this
        //timer =
        countDownTimer = object: CountDownTimer(10000, 100) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                for (observer in timerObservers) {
                    observer.onTick(Duration.ofMillis(millisUntilFinished))
                }
            }
            override fun onFinish() {
                serviceInstance = null
                stopSelf()
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }



    override fun subscribe(timerObserver: TimerObserver) {
        timerObservers.add(timerObserver)
    }

    override fun unSubscribe(timerObserver: TimerObserver) {
        timerObservers.remove(timerObserver)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}




