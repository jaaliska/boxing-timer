package by.itman.boxingtimer.data

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import by.itman.boxingtimer.R
import javax.inject.Inject


class AppPreferences @Inject constructor(private val prefs: SharedPreferences) {
    private val DARK_THEME = "dark_theme"


    fun isDarkTheme(): Boolean {
        return prefs.getBoolean(DARK_THEME, false)
    }

    fun getThemeResource(): Int {
        return if (isDarkTheme()) R.style.MyDarkTheme else R.style.MyLightTheme
    }

    fun setDarkTheme(darkTheme: Boolean) {
        val editor: Editor = prefs.edit()
        editor.putBoolean(DARK_THEME, darkTheme)
        editor.apply()
    }

}