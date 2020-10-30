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
import javax.inject.Inject

@AndroidEntryPoint
class EditingActivity : AppCompatActivity(), EditingView {
    private lateinit var mTxtEditingTitleName: TextView
    private lateinit var mTxtEditingTimerName: TextView
    private lateinit var mLayEditingRoundDur: View
    private lateinit var setTitMLayEditingRoundDur: TextView
    private lateinit var setValMLayEditingRoundDur: TextView
    private lateinit var mLayEditingRestDur: View
    private lateinit var setTitMLayEditingRestDur: TextView
    private lateinit var setValMLayEditingRestDur: TextView
    private lateinit var mLayEditingRoundQua: View
    private lateinit var setTitMLayEditingRoundQua: TextView
    private lateinit var setValMLayEditingRoundQua: TextView
    private lateinit var mLayEditingRunUp: View
    private lateinit var setTitMLayEditingRunUp: TextView
    private lateinit var setValMLayEditingRunUp: TextView
    private lateinit var mLayEditingNoticeOfEndRound: View
    private lateinit var setTitMLayEditingNoticeOfEndRound: TextView
    private lateinit var setValMLayEditingNoticeOfEndRound: TextView
    private lateinit var mLayEditingSoundType: View
    private lateinit var setTitMLayEditingSoundType: TextView
    private lateinit var setValMLayEditingSoundType: TextView

    @Inject
    lateinit var editingPresenter: EditingPresenter
    private val myDialog: MyAlertDialogs = MyAlertDialogs()
    private val myUtils: MyUtils = MyUtils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing)

        mTxtEditingTitleName = findViewById(R.id.txt_settings_title)
        mTxtEditingTimerName = findViewById(R.id.txt_settings_timer_name)
        mLayEditingRoundDur = findViewById(R.id.editing_layout_roundDuration)
        setTitMLayEditingRoundDur = mLayEditingRoundDur.findViewById(R.id.txt_editing_title)
        setValMLayEditingRoundDur = mLayEditingRoundDur.findViewById(R.id.txt_editing_value)
        setTitMLayEditingRoundDur.setText(R.string.txt_title_name_roundDuration)
        mLayEditingRestDur = findViewById(R.id.editing_layout_restDuration)
        setTitMLayEditingRestDur = mLayEditingRestDur.findViewById(R.id.txt_editing_title)
        setValMLayEditingRestDur = mLayEditingRestDur.findViewById(R.id.txt_editing_value)
        setTitMLayEditingRestDur.setText(R.string.txt_title_name_restDuration)
        mLayEditingRoundQua = findViewById(R.id.editing_layout_roundQuantity)
        setTitMLayEditingRoundQua = mLayEditingRoundQua.findViewById(R.id.txt_editing_title)
        setValMLayEditingRoundQua = mLayEditingRoundQua.findViewById(R.id.txt_editing_value)
        setTitMLayEditingRoundQua.setText(R.string.txt_title_name_roundQuantity)
        mLayEditingRunUp = findViewById(R.id.editing_layout_runUp)
        setTitMLayEditingRunUp = mLayEditingRunUp.findViewById(R.id.txt_editing_title)
        setValMLayEditingRunUp = mLayEditingRunUp.findViewById(R.id.txt_editing_value)
        setTitMLayEditingRunUp.setText(R.string.txt_title_name_runUp)
        mLayEditingNoticeOfEndRound = findViewById(R.id.editing_layout_noticeOfEndRound)
        setTitMLayEditingNoticeOfEndRound =
            mLayEditingNoticeOfEndRound.findViewById(R.id.txt_editing_title)
        setValMLayEditingNoticeOfEndRound =
            mLayEditingNoticeOfEndRound.findViewById(R.id.txt_editing_value)
        setTitMLayEditingNoticeOfEndRound.setText(R.string.txt_title_name_noticeOfEndRound)
        mLayEditingSoundType = findViewById(R.id.editing_layout_soundType)
        setTitMLayEditingSoundType = mLayEditingSoundType.findViewById(R.id.txt_editing_title)
        setValMLayEditingSoundType = mLayEditingSoundType.findViewById(R.id.txt_editing_value)
        setTitMLayEditingSoundType.setText(R.string.txt_title_name_soundType)

        editingPresenter.setView(this)

        val arguments = intent.extras
        val id = arguments?.getInt("id")
        if (id != null) {
            editingPresenter.setActualTimerId(id)
        }

        mTxtEditingTimerName.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.NAME)
        }

        mLayEditingRoundDur.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.ROUND_DURATION)
        }

        mLayEditingRestDur.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.REST_DURATION)
        }

        mLayEditingRoundQua.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.ROUND_QUANTITY)
        }

        mLayEditingRoundDur.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.ROUND_DURATION)
        }

        mLayEditingRunUp.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.RUN_UP)
        }

        mLayEditingNoticeOfEndRound.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.NOTICE_OF_END_ROUND)
        }

        mLayEditingSoundType.setOnClickListener() {
            editingPresenter.onFieldClick(TimerField.SOUND_TYPE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showDurationDialog(title: Int, time: Duration, consumer: (Duration) -> Unit) {
        myDialog.alertDialogForDuration(
            context = this,
            title = title,
            time = time,
            consumer = consumer
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showNumberDialog(title: Int, number: Int, consumer: (Int) -> Unit) {
        myDialog.alertDialogForNumber(
            context = this,
            title = title,
            value = number,
            consumer = consumer
        )
    }

    override fun showSoundTypeDialog(title: Int, sound: TimerSoundType, consumer: (TimerSoundType) -> Unit) {
        myDialog.alertDialogForSound(
            context = this,
            title = title,
            value = sound,
            consumer = consumer
        )
    }

    override fun showStringDialog(title: Int, string: String, consumer: (String) -> Unit) {
        myDialog.alertDialogForString(
            context = this,
            title = title,
            string = string,
            consumer = consumer
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun displayTimerFields(timerPresentation: TimerPresentation) {
        mTxtEditingTimerName.text = timerPresentation.getName()
        setValMLayEditingRoundDur.text =
            myUtils.formatDuration(timerPresentation.getRoundDuration())
        setValMLayEditingRestDur.text = myUtils.formatDuration(timerPresentation.getRestDuration())
        setValMLayEditingRoundQua.text = timerPresentation.getRoundQuantity().toString()
        setValMLayEditingRunUp.text = myUtils.formatDuration(timerPresentation.getRunUp())
        setValMLayEditingNoticeOfEndRound.text =
            myUtils.formatDuration(timerPresentation.getNoticeOfEndRound())
        setValMLayEditingSoundType.text = timerPresentation.getSoundType().toString()
    }
}