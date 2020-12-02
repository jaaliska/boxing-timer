package by.itman.boxingtimer.ui.editing

import by.itman.boxingtimer.R
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.models.TimerField
import by.itman.boxingtimer.models.TimerPresentationImpl
import by.itman.boxingtimer.models.TimerPresentation

import moxy.MvpPresenter
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

            TimerField.NOTICE_OF_END_REST ->
                view.showDurationDialog(
                    title = R.string.txt_title_name_noticeOfEndRest,
                    time = timer.noticeOfEndRest,
                    consumer = { duration ->
                        timer.noticeOfEndRest = duration
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )


            TimerField.SOUND_TYPE_OF_END_ROUND_NOTICE ->
                view.showSoundTypeDialog(
                    title = R.string.txt_title_name_soundType_of_end_round_notice,
                    sound = timer.soundTypeOfEndRoundNotice,
                    consumer = { sound ->
                        timer.soundTypeOfEndRoundNotice = sound
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.SOUND_TYPE_OF_END_REST_NOTICE ->
                view.showSoundTypeDialog(
                    title = R.string.txt_title_name_soundType_of_end_rest_notice,
                    sound = timer.soundTypeOfEndRestNotice,
                    consumer = { sound ->
                        timer.soundTypeOfEndRestNotice = sound
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.SOUND_TYPE_OF_START_ROUND ->
                view.showSoundTypeDialog(
                    title = R.string.txt_title_name_soundType_of_start_round,
                    sound = timer.soundTypeOfStartRound,
                    consumer = { sound ->
                        timer.soundTypeOfStartRound = sound
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )

            TimerField.SOUND_TYPE_OF_START_REST ->
                view.showSoundTypeDialog(
                    title = R.string.txt_title_name_soundType_of_start_rest,
                    sound = timer.soundTypeOfStartRest,
                    consumer = { sound ->
                        timer.soundTypeOfStartRest = sound
                        timerProvider.save(timer)
                        view.displayTimerFields(timerPresentation)
                    }
                )
        }
    }
}