package by.itman.boxingtimer.ui.settings

import android.content.Context
import android.widget.ListAdapter
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.utils.MyAlertDialogs
import java.time.Duration
import javax.inject.Inject

class SettingsPresenterImpl
@Inject constructor(private val timerProvider: TimerProvider): SettingsPresenter {

    private lateinit var view: SettingsView
    private val myDialog: MyAlertDialogs = MyAlertDialogs()


    override fun setView(view: SettingsView) {
        this.view = view
    }

    override fun onCreateNewTimer(context: Context) {
        myDialog.alertDialogForString(
            context = context,
            title = R.string.settings_txt_dialog_enter_timer_name,
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
                view.startEdition(tm.id!!)
            }
        )
    }

    override fun setAdapter(context: Context, resource: Int): ListAdapter {
        return SettingsAdapter(
            context, R.layout.settings_fragment, timerProvider.getAll(),
            timerProvider = timerProvider
        )
    }
}