package exe.weazy.memes.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import exe.weazy.memes.R
import exe.weazy.memes.ui.login.LoginActivity
import exe.weazy.memes.ui.main.MainActivity
import exe.weazy.memes.ui.main.MainViewModel
import exe.weazy.memes.util.extensions.useViewModel
import exe.weazy.memes.util.values.EMPTY_STRING

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = useViewModel(this, MainViewModel::class.java)

        Handler().postDelayed({
            if (viewModel.getUserToken() != EMPTY_STRING) {
                openMainScreen()
            } else {
                openLoginScreen()
            }
        }, 300)
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
