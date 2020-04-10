package exe.weazy.memes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import exe.weazy.memes.R
import exe.weazy.memes.util.showErrorSnackbar
import exe.weazy.memes.util.useViewModel
import exe.weazy.memes.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = useViewModel(this, LoginViewModel::class.java)

        initListeners()
    }

    private fun initListeners() {
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                passwordEditTextLayout.helperText = getString(R.string.password_length)
            } else {
                passwordEditTextLayout.helperText = ""
            }
        }

        loginEditText.addTextChangedListener {
            textFieldValidation(loginEditText, loginEditTextLayout)
        }

        passwordEditText.addTextChangedListener {
            textFieldValidation(passwordEditText, passwordEditTextLayout)
        }

        signInButton.setOnClickListener {
            if (validate(passwordEditText.text.toString()) && validate(loginEditText.text.toString())) {
                // sign in
            } else {
                showErrorSnackbar(R.string.wrong_credentials, rootViewLogin)
            }
        }
    }

    private fun textFieldValidation(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout) {
        if (!validate(textInputEditText.text.toString())) {
            textInputLayout.error = getString(R.string.field_can_not_be_empty)
        } else {
            textInputLayout.error = ""
        }
    }

    private fun validate(text: String) = text.isNotEmpty()
}
