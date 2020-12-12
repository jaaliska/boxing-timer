package by.itman.boxingtimer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.itman.boxingtimer.R
import by.itman.boxingtimer.data.AppPreferences
import javax.inject.Inject

open class BaseActivity: AppCompatActivity() {

    private var themeResId: Int = R.style.MyLightTheme

    @Inject
    lateinit var appPreferences: AppPreferences

   override fun setTheme(resId: Int) {
       super.setTheme(resId)
       themeResId = resId
   }

   override fun onCreate(savedInstanceState: Bundle?) {
       setTheme(appPreferences.getThemeResource())
       super.onCreate(savedInstanceState)
   }

   override fun onResume() {
       super.onResume()

       if (themeResId != appPreferences.getThemeResource()) {
           recreate()
       }
   }
}