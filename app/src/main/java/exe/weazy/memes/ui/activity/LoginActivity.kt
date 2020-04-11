package exe.weazy.memes.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import exe.weazy.memes.R
import exe.weazy.memes.state.LoginState
import exe.weazy.memes.util.PREFERENCES_FILENAME
import exe.weazy.memes.util.saveUserData
import exe.weazy.memes.util.showErrorSnackbar
import exe.weazy.memes.util.useViewModel
import exe.weazy.memes.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
        viewModel = useViewModel(this, LoginViewModel::class.java)

        initListeners()
    }

    private fun initListeners() {
        viewModel.state.observe(this, Observer {
            setState(it)
        })

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
            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validate(password) && validate(login)) {
                viewModel.signIn(login, password)
            } else {
                viewModel.state.postValue(LoginState.Error())
            }
        }
    }

    private fun setState(state: LoginState) {
        when(state) {
            is LoginState.Default -> {
                makeButtonLoading(false)
            }
            is LoginState.Loading -> {
                makeButtonLoading(true)
            }
            is LoginState.Error -> {
                makeButtonLoading(false)
                showErrorSnackbar(R.string.wrong_credentials, rootViewLogin)
            }
            is LoginState.Success -> {
                saveUserData(prefs, state.token, state.userInfo)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
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

    private fun makeButtonLoading(isLoading: Boolean) {
        if (isLoading) {
            signInButton.text = ""
            loadingBar.isVisible = true
        } else {
            signInButton.text = getString(R.string.sign_in)
            loadingBar.isVisible = false
        }
    }
}
