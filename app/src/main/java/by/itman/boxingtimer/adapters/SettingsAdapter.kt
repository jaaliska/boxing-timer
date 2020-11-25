package by.itman.boxingtimer.adapters

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
import by.itman.boxingtimer.activities.EditingActivity
import by.itman.boxingtimer.models.TimerModel
import by.itman.boxingtimer.providers.TimerProvider
import by.itman.boxingtimer.providers.TimerProviderImpl
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class SettingsAdapter(
    context: Context,
    resource: Int,
    data: List<TimerModel>,
    val timerProvider: TimerProvider
) :
    ArrayAdapter<TimerModel>(context, resource, data) {

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
            val tm = getItem(position)
            if (tm != null) {
                //TODO AlertDialog
                timerProvider.remove(tm)
                remove(tm)
            }
        }
        return view
    }

}