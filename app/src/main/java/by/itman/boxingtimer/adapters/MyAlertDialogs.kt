package by.itman.boxingtimer.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.DialogTitle
import by.itman.boxingtimer.R
import by.itman.boxingtimer.activities.EditingActivity
import by.itman.boxingtimer.filters.MyTextWatcher
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.models.TimerSoundType
import java.time.Duration

class MyAlertDialogs {


    /**
     * Method creates a dialog for changing the value of Duration by user
     * @param context is Application Context
     * @param title is title name of dialog
     * @param time initial value of Duration.  It's necessary to draw a dialog.
     * @param consumer adopt new value of Duration
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun alertDialogForDuration(
        context: Context,
        title: Int,
        time: Duration,
        consumer: (Duration) -> Unit
    ) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val subView: View = inflater.inflate(R.layout.alert_dialog_time, null)
        val editMin: EditText = subView.findViewById(R.id.edit_txt_main_time_min)
        val editSec: EditText = subView.findViewById(R.id.edit_txt_main_time_sec)
        val mMin: Long = time.toMinutes()
        val mSec: Long = time.seconds - mMin * 60
        editMin.setText(String.format("%02d", mMin))
        editSec.setText(String.format("%02d", mSec))
        editMin.addTextChangedListener(MyTextWatcher(editMin, 0, 60))
        editSec.addTextChangedListener(MyTextWatcher(editSec, 0, 60))
        editSec.setSelectAllOnFocus(true)

        @SuppressLint("RestrictedApi")
        val dialogTitle = DialogTitle(context)
        dialogTitle.setText(title)
        dialogTitle.gravity = Gravity.CENTER
        dialogTitle.textSize = 30F
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCustomTitle(dialogTitle)
            .setView(subView)
            .setNegativeButton("Отмена") { dialogInterface: DialogInterface?, i: Int ->
                return@setNegativeButton
            }
            .setPositiveButton("Ok") setNegativeButton@{ dialogInterface: DialogInterface?, i: Int ->
                if (editMin.text.isEmpty() || editSec.text.isEmpty()) return@setNegativeButton
                val min: Int = editMin.editableText.toString().toInt()
                val sec: Int = editSec.editableText.toString().toInt()
                consumer(Duration.ofSeconds(min * 60L + sec))
            }
        builder.create()
        builder.show().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    //TODO отвалидировать входящий ноль
    @RequiresApi(Build.VERSION_CODES.O)
    fun alertDialogForNumber(
        context: Context,
        title: Int,
        value: Int,
        consumer: (Int) -> Unit
    ) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val subView: View = inflater.inflate(R.layout.alert_dialog_number, null)
        val editValue: EditText = subView.findViewById(R.id.edit_txt_main_value)
        var changeValue = value
        editValue.setText("$changeValue")
        editValue.setOnClickListener { view: View? ->
            editValue.selectAll()
        }
        editValue.addTextChangedListener(MyTextWatcher(editValue, 1, 100))
        val minusValue: Button = subView.findViewById(R.id.button_main_value_minus)
        minusValue.setOnClickListener {
            if (changeValue > 1) {
                changeValue -= 1
                editValue.setText("$changeValue")
            }
        }
        val plusValue: Button = subView.findViewById(R.id.button_main_value_plus)
        plusValue.setOnClickListener {
            changeValue += 1
            editValue.setText("$changeValue")
        }
        @SuppressLint("RestrictedApi")
        val dialogTitle = DialogTitle(context)
        dialogTitle.setText(title)
        dialogTitle.gravity = Gravity.CENTER
        dialogTitle.textSize = 30F
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCustomTitle(dialogTitle)
            .setView(subView)
            .setNegativeButton("Отмена") { dialogInterface: DialogInterface?, i: Int ->
                return@setNegativeButton
            }
            .setPositiveButton("Ok") setNegativeButton@{ dialogInterface: DialogInterface?, i: Int ->
                if (editValue.text.isEmpty()) return@setNegativeButton
                consumer(editValue.text.toString().toInt())
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    fun alertDialogForString(
        context: Context,
        title: Int,
        string: String?,
        consumer: (String) -> Unit
    ) {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val subView: View = inflater.inflate(R.layout.alert_dialog_edit_text, null)
        val editName: EditText = subView.findViewById(R.id.edit_txt_enter_string_dialog)
        if (string != null) editName.setText(string)

        @SuppressLint("RestrictedApi")
        val dialogTitle = DialogTitle(context)
        dialogTitle.setText(title)
        dialogTitle.gravity = Gravity.CENTER
        dialogTitle.textSize = 20F
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCustomTitle(dialogTitle)
            .setView(subView)
            .setNegativeButton("Отмена") { dialogInterface: DialogInterface?, i: Int ->
                return@setNegativeButton
            }
            .setPositiveButton("Ok") setNegativeButton@{ dialogInterface: DialogInterface?, i: Int ->
                val name = editName.text.toString()
                if (name.isEmpty()) return@setNegativeButton
                consumer(name)
            }
        builder.create()
        builder.show().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

    }


}