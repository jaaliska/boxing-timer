package by.itman.boxingtimer.filters

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

//TODO убрать ведущие нули
class MyTextWatcher(private val editText: EditText, private val min: Int, private val max: Int):
    TextWatcher {
    var changeTime: Int = 0
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(p0: Editable?) {
        if (p0 != null && p0.isEmpty()) return
        if (p0.toString().toInt() in min until max) {
            changeTime = p0.toString().toInt()
        } else {
            if (editText.hasFocus()) {
                editText.setText(String.format("%02d", changeTime))
                editText.setSelection(2)
            }
        }
    }
}