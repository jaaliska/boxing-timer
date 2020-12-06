package by.itman.boxingtimer.ui.editing

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.itman.boxingtimer.R
import by.itman.boxingtimer.utils.MyAlertDialogs
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.models.TimerField
import by.itman.boxingtimer.models.TimerPresentation
import by.itman.boxingtimer.utils.timerFormat
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditingActivity : AppCompatActivity(), EditingView {
    private val fieldLayoutsMap: EnumMap<TimerField, View> = EnumMap(TimerField::class.java)

    @Inject
    lateinit var editingPresenter: EditingPresenter
    private val myDialog: MyAlertDialogs = MyAlertDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing)
        initialiseView()
        editingPresenter.setView(this)
        val arguments = intent.extras
        val id = arguments?.getInt("id")
        if (id != null) {
            editingPresenter.setActualTimerId(id)
        }
    }


    //TODO add include layout
    private fun initialiseView() {
        fieldLayoutsMap[TimerField.NAME] = findViewById(R.id.txt_settings_timer_name)
        fieldLayoutsMap[TimerField.ROUND_DURATION] = findViewById(R.id.editing_layout_roundDuration)
        fieldLayoutsMap[TimerField.REST_DURATION] = findViewById(R.id.editing_layout_restDuration)
        fieldLayoutsMap[TimerField.ROUND_QUANTITY] = findViewById(R.id.editing_layout_roundQuantity)
        fieldLayoutsMap[TimerField.RUN_UP] = findViewById(R.id.editing_layout_runUp)
        fieldLayoutsMap[TimerField.NOTICE_OF_END_ROUND] =
            findViewById(R.id.editing_layout_noticeOfEndRound)
        fieldLayoutsMap[TimerField.NOTICE_OF_END_REST] =
            findViewById(R.id.editing_layout_noticeOfEndRest)
        fieldLayoutsMap[TimerField.SOUND_TYPE_OF_END_ROUND_NOTICE] =
            findViewById(R.id.editing_layout_soundTypeOfEndRoundNotice)
        fieldLayoutsMap[TimerField.SOUND_TYPE_OF_END_REST_NOTICE] =
            findViewById(R.id.editing_layout_soundTypeOfEndRestNotice)
        fieldLayoutsMap[TimerField.SOUND_TYPE_OF_START_ROUND] =
            findViewById(R.id.editing_layout_soundTypeOfStartRound)
        fieldLayoutsMap[TimerField.SOUND_TYPE_OF_START_REST] =
            findViewById(R.id.editing_layout_soundTypeOfStartRest)

        fieldLayoutsMap.forEach { layout ->
            layout.value.setOnClickListener() {
                editingPresenter.onFieldClick(layout.key)
            }
        }

        fieldLayoutsMap.forEach { layout ->
            if (layout.key != TimerField.NAME)
                layout.value.findViewById<TextView>(R.id.txt_editing_title)
                    .text = TimerField.values()[layout.key.ordinal].getTitle()
        }
    }

    override fun displayTimerFields(timerPresentation: TimerPresentation) {
        val nameField: TextView = fieldLayoutsMap[TimerField.NAME] as TextView
        nameField.text = timerPresentation.getName()
        setFieldEditingValue(
            TimerField.ROUND_DURATION,
            timerPresentation.getRoundDuration().timerFormat()
        )
        setFieldEditingValue(
            TimerField.REST_DURATION,
            timerPresentation.getRestDuration().timerFormat()
        )
        setFieldEditingValue(
            TimerField.ROUND_QUANTITY,
            timerPresentation.getRoundQuantity().toString()
        )
        setFieldEditingValue(
            TimerField.RUN_UP,
            timerPresentation.getRunUp().timerFormat()
        )
        setFieldEditingValue(
            TimerField.NOTICE_OF_END_ROUND,
            timerPresentation.getNoticeOfEndRound().timerFormat()
        )
        setFieldEditingValue(
            TimerField.NOTICE_OF_END_REST,
            timerPresentation.getNoticeOfEndRest().timerFormat()
        )
        setFieldEditingValue(
            TimerField.SOUND_TYPE_OF_END_ROUND_NOTICE,
            timerPresentation.getSoundTypeOfEndRoundNotice().toString()
        )
        setFieldEditingValue(
            TimerField.SOUND_TYPE_OF_END_REST_NOTICE,
            timerPresentation.getSoundTypeOfEndRestNotice().toString()
        )
        setFieldEditingValue(
            TimerField.SOUND_TYPE_OF_START_ROUND,
            timerPresentation.getSoundTypeOfStartRound().toString()
        )
        setFieldEditingValue(
            TimerField.SOUND_TYPE_OF_START_REST,
            timerPresentation.getSoundTypeOfStartRest().toString()
        )
    }

    private fun setFieldEditingValue(timerField: TimerField, value: String) {
        fieldLayoutsMap[timerField]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = value
    }

    override fun showDurationDialog(
        title: Int,
        time: Duration,
        consumer: (Duration) -> Unit
    ) {
        myDialog.alertDialogForDuration(
            context = this,
            title = title,
            time = time,
            consumer = consumer
        )
    }

    override fun showNumberDialog(
        title: Int,
        number: Int,
        consumer: (Int) -> Unit
    ) {
        myDialog.alertDialogForNumber(
            context = this,
            title = title,
            value = number,
            consumer = consumer
        )
    }

    override fun showSoundTypeDialog(
        title: Int,
        sound: TimerSoundType,
        consumer: (TimerSoundType) -> Unit
    ) {
        myDialog.alertDialogForSound(
            context = this,
            title = title,
            value = sound,
            consumer = consumer
        )
    }

    override fun showStringDialog(
        title: Int,
        string: String,
        consumer: (String) -> Unit
    ) {
        myDialog.alertDialogForString(
            context = this,
            title = title,
            string = string,
            consumer = consumer
        )
    }
}