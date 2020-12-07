package by.itman.boxingtimer.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerPresentationImpl
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.ui.run.RunActivity
import by.itman.boxingtimer.ui.settings.SettingsActivity
import by.itman.boxingtimer.utils.MyAlertDialogs
import moxy.MvpPresenter
import java.time.Duration
import javax.inject.Inject

class MainPresenterImpl
@Inject constructor(private val timerProvider: TimerProvider): MvpPresenter<MainView>(), MainPresenter {
    private lateinit var mainView: MainView
    private var activeTimerModel: TimerModel = getActiveTimerModel()
    private val mDialogs: MyAlertDialogs = MyAlertDialogs()

    override fun setView(view: MainView) {
        this.mainView = view
    }

    override fun onClickRoundDuration(context: Context) {
        mDialogs.alertDialogForDuration(
            context = context,
            title = R.string.txt_title_name_roundDuration,
            time = activeTimerModel.roundDuration,
            consumer = { duration ->
                activeTimerModel.roundDuration = duration
                timerProvider.save(activeTimerModel)
                mainView.updateView(TimerPresentationImpl(activeTimerModel))
            }
        )
    }

    override fun onClickRestDuration(context: Context) {
        mDialogs.alertDialogForDuration(
            context = context,
            title = R.string.txt_title_name_restDuration,
            time = activeTimerModel.restDuration,
            consumer = { duration ->
                activeTimerModel.restDuration = duration
                mainView.updateView(TimerPresentationImpl(activeTimerModel))
            }
        )
    }

    override fun onClickRoundQuantity(context: Context) {
        mDialogs.alertDialogForNumber(
            context = context,
            title = R.string.txt_title_name_roundQuantity,
            value = activeTimerModel.roundQuantity,
            consumer = { Int ->
                activeTimerModel.roundQuantity = Int
                mainView.updateView(TimerPresentationImpl(activeTimerModel))
            }
        )
    }

    override fun onClickStart(context: Context) {
        val intent = Intent(context, RunActivity::class.java)
        intent.putExtra("id", activeTimerModel.id)
        mainView.startActivityWith(intent)
    }

    override fun onClickSettings(context: Context) {
        val intent = Intent(context, SettingsActivity::class.java)
        mainView.startActivityWith(intent)
    }

    override fun onPausedActivity() {
        timerProvider.save(activeTimerModel)
        setActiveTimerModel(activeTimerModel)
    }

    override fun onResumedActivity() {
        activeTimerModel = getActiveTimerModel()
        mainView.updateView(TimerPresentationImpl(activeTimerModel))
    }

    private fun getActiveTimerModel(): TimerModel {
        val timersModel = timerProvider.getAll()
        return  if (timersModel.isNotEmpty()) {
            if (timerProvider.getActiveTimer() != null) {
                timerProvider.getActiveTimer()!!
            } else {
                timersModel[0]
            }
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

    private fun setActiveTimerModel(timer: TimerModel) {
        timerProvider.setActiveTimer(timer)
    }


    override fun setSpinner(context: Context, spinner: Spinner) {
        val data: List<TimerModel> = timerProvider.getAll()
        val adapter: ArrayAdapter<TimerModel> = ArrayAdapter(context, R.layout.spinner_item, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(data.indexOf(activeTimerModel))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                activeTimerModel = parent?.getItemAtPosition(position) as TimerModel
                mainView.updateView(TimerPresentationImpl(activeTimerModel))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}