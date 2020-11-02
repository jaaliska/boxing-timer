package by.itman.boxingtimer.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.itman.boxingtimer.R
import by.itman.boxingtimer.adapters.MyAlertDialogs
import by.itman.boxingtimer.models.TimerSoundType
import by.itman.boxingtimer.presenters.EditingPresenter
import by.itman.boxingtimer.presenters.TimerField
import by.itman.boxingtimer.utils.MyUtils
import by.itman.boxingtimer.views.EditingView
import by.itman.boxingtimer.views.TimerPresentation
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditingActivity : AppCompatActivity(), EditingView {
    private val layoutMap: EnumMap<TimerField, View> = EnumMap(TimerField::class.java)

    @Inject
    lateinit var editingPresenter: EditingPresenter
    private val myDialog: MyAlertDialogs = MyAlertDialogs()
    private val myUtils: MyUtils = MyUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing)
        viewInitialisation()
        editingPresenter.setView(this)
        val arguments = intent.extras
        val id = arguments?.getInt("id")
        if (id != null) {
            editingPresenter.setActualTimerId(id)
        }
    }

    private fun viewInitialisation() {
        layoutMap[TimerField.NAME] = findViewById(R.id.txt_settings_timer_name)
        layoutMap[TimerField.ROUND_DURATION] = findViewById(R.id.editing_layout_roundDuration)
        layoutMap[TimerField.REST_DURATION] = findViewById(R.id.editing_layout_restDuration)
        layoutMap[TimerField.ROUND_QUANTITY] = findViewById(R.id.editing_layout_roundQuantity)
        layoutMap[TimerField.RUN_UP] = findViewById(R.id.editing_layout_runUp)
        layoutMap[TimerField.NOTICE_OF_END_ROUND] = findViewById(R.id.editing_layout_noticeOfEndRound)
        layoutMap[TimerField.SOUND_TYPE] = findViewById(R.id.editing_layout_soundType)

        layoutMap.forEach { layout ->
            layout.value.setOnClickListener() {
                editingPresenter.onFieldClick(layout.key)
            }
        }

        layoutMap.forEach { layout ->
            if (layout.key != TimerField.NAME)
                layout.value.findViewById<TextView>(R.id.txt_editing_title)
                    .text = TimerField.values()[layout.key.ordinal].getNameTitle()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun displayTimerFields(timerPresentation: TimerPresentation) {
        val nameField: TextView = layoutMap[TimerField.NAME] as TextView
        nameField.text = timerPresentation.getName()
        layoutMap[TimerField.ROUND_DURATION]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = myUtils.formatDuration(timerPresentation.getRoundDuration())
        layoutMap[TimerField.REST_DURATION]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = myUtils.formatDuration(timerPresentation.getRestDuration())
        layoutMap[TimerField.ROUND_QUANTITY]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = timerPresentation.getRoundQuantity().toString()
        layoutMap[TimerField.RUN_UP]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = myUtils.formatDuration(timerPresentation.getRunUp())
        layoutMap[TimerField.NOTICE_OF_END_ROUND]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = myUtils.formatDuration(timerPresentation.getNoticeOfEndRound())
        layoutMap[TimerField.SOUND_TYPE]?.findViewById<TextView>(R.id.txt_editing_value)
            ?.text = timerPresentation.getSoundType().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showDurationDialog(
        title: Int,
        time: Duration,
        consumer: (Duration) -> Unit) {
        myDialog.alertDialogForDuration(
            context = this,
            title = title,
            time = time,
            consumer = consumer
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showNumberDialog(
        title: Int,
        number: Int,
        consumer: (Int) -> Unit) {
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
        consumer: (String) -> Unit) {
        myDialog.alertDialogForString(
            context = this,
            title = title,
            string = string,
            consumer = consumer
        )
    }
}