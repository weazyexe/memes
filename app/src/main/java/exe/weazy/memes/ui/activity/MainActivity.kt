package exe.weazy.memes.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import exe.weazy.memes.R
import exe.weazy.memes.util.PREFERENCES_FILENAME
import exe.weazy.memes.util.getUserInfo

class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
        val userInfo = getUserInfo(prefs)

        Toast.makeText(this, userInfo.username, Toast.LENGTH_LONG).show()
    }
}
