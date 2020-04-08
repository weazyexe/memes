package exe.weazy.memes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val paddingBottom = signInButton.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(signInButton) { view, insets ->
            view.updatePadding(
                bottom = insets.systemWindowInsetBottom + paddingBottom,
                left = view.paddingLeft,
                right = view.paddingRight
            )
            insets
        }
    }
}
