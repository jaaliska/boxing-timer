package by.itman.boxingtimer.ui.settings

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import by.itman.boxingtimer.R
import by.itman.boxingtimer.ui.editing.EditingActivity
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.data.TimerProvider
import by.itman.boxingtimer.utils.MyAlertDialogs


class SettingsAdapter(
    context: Context,
    resource: Int,
    data: List<TimerModel>,
    val timerProvider: TimerProvider
) :
    ArrayAdapter<TimerModel>(context, resource, data) {

    private val myDialog: MyAlertDialogs = MyAlertDialogs()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        view = if (convertView == null) {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.settings_fragment, parent, false)
        } else {
            convertView
        }

        val textView: TextView = view.findViewById(R.id.txt_settings_fragment_timer_name)
        textView.text = getItem(position).toString()
        textView.setOnClickListener {
            val tm = getItem(position)
            if (tm != null) {
                val intent = Intent(context, EditingActivity::class.java)
                    .addFlags(FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("id", tm.id)
                startActivity(context, intent, null)
            }
        }

        val removeButton: Button = view.findViewById(R.id.button_settings_fragment_remove)
        removeButton.setOnClickListener {
            myDialog.alertDialogForActionVerification(
                context = context,
                title = R.string.txt_sittings_dialog_remove,
                consumer = {
                    val tm = getItem(position)
                    if (tm != null) {
                        timerProvider.remove(tm)
                        remove(tm)
                    }
                }
            )
        }
        return view
    }

}