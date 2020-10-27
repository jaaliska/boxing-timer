package by.itman.boxingtimer.presenters

import android.widget.Toast
import by.itman.boxingtimer.R
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.views.EditingView
import by.itman.boxingtimer.views.TimerPresentation

import moxy.MvpPresenter
import java.lang.Exception
import java.time.Duration
import javax.inject.Inject


class EditingPresenterImpl
@Inject constructor(private val timerProvider: TimerProvider) :
    MvpPresenter<EditingView>(), EditingPresenter {

    private lateinit var view: EditingView
    private lateinit var timer: TimerModel
    private lateinit var timerPresentation: TimerPresentation

    override fun setView(view: EditingView) {
        this.view = view
    }

    override fun setActualTimerId(id: Int) {
        timerProvider.getById(id)?.let { timer = it }
        timerPresentation = TimerPresentationImpl(timer)
        view.displayTimerFields(timerPresentation)
    }

    override fun onFieldClick(timerField: TimerField) {
        when (timerField) {
            TimerField.NAME ->
                view.showStringDialog(
                    title = R.string.txt_editing_timer_name,
                    string = timer.name,
                    consumer = { string ->
                        timer.name = string
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.ROUND_DURATION ->
                view.showDurationDialog(
                    title = R.string.txt_title_name_roundDuration,
                    time = timer.roundDuration,
                    consumer = { duration ->
                        timer.roundDuration = duration
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.REST_DURATION ->
                view.showDurationDialog(
                    title = R.string.txt_title_name_restDuration,
                    time = timer.restDuration,
                    consumer = { duration ->
                        timer.restDuration = duration
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.ROUND_QUANTITY ->
                view.showNumberDialog(
                    title = R.string.txt_title_name_roundQuantity,
                    number = timer.roundQuantity,
                    consumer = { int ->
                        timer.roundQuantity = int
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )
            TimerField.RUN_UP ->
                view.showDurationDialog(
                    title = R.string.txt_title_name_runUp,
                    time = timer.runUp,
                    consumer = { duration ->
                        timer.runUp = duration
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.NOTICE_OF_END_ROUND ->
                view.showDurationDialog(
                    title = R.string.txt_title_name_noticeOfEndRound,
                    time = timer.noticeOfEndRound,
                    consumer = { duration ->
                        timer.noticeOfEndRound = duration
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )
            TimerField.SOUND_TYPE ->
                view.showSoundTypeDialog {
                    //todo SoundTypeDialog
                }
        }
    }

}