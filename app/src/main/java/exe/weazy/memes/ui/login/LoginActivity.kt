package exe.weazy.memes.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import exe.weazy.memes.R
import exe.weazy.memes.state.LoginState
import exe.weazy.memes.ui.main.MainActivity
import exe.weazy.memes.util.extensions.showErrorSnackbar
import exe.weazy.memes.util.extensions.useViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = useViewModel(this, LoginViewModel::class.java)

        initObservers()
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
            if (!viewModel.validateLogin(it.toString())) {
                loginEditText.error = getString(R.string.field_can_not_be_empty)
            } else {
                loginEditText.error = ""
            }
        }

        passwordEditText.addTextChangedListener {
            if (!viewModel.validatePassword(it.toString())) {
                passwordEditText.error = getString(R.string.field_can_not_be_empty)
            } else {
                passwordEditText.error = ""
            }
        }

        signInButton.setOnClickListener {
            viewModel.signIn(
                login = loginEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.state.observe(this, Observer {
            setState(it)
        })
    }

    private fun setState(state: LoginState) {
        when(state) {
            LoginState.DEFAULT -> {
                makeButtonLoading(false)
            }
            LoginState.LOADING -> {
                makeButtonLoading(true)
            }
            LoginState.ERROR -> {
                makeButtonLoading(false)
                showErrorSnackbar(R.string.wrong_credentials, rootViewLogin)
            }
            LoginState.SUCCESS -> {
                viewModel.let {
                    it.saveUserData(it.userInfo, it.accessToken, this)
                }
                openMainScreen()
            }
        }
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

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