package exe.weazy.memes.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.entity.UserInfo
import exe.weazy.memes.network.NetworkRepository
import exe.weazy.memes.state.LoginState
import exe.weazy.memes.storage.UserStorage
import exe.weazy.memes.util.extensions.isValidLogin
import exe.weazy.memes.util.extensions.isValidPassword
import exe.weazy.memes.util.extensions.subscribe
import io.reactivex.disposables.Disposable

class LoginViewModel : ViewModel() {

    private val repository = NetworkRepository()
    private lateinit var signInDisposable: Disposable

    val state = MutableLiveData(LoginState.DEFAULT)
    lateinit var userInfo: UserInfo
    lateinit var accessToken: String

    fun signIn(login: String, password: String) {
        if (validateLogin(login) && validatePassword(password)) {
            state.postValue(LoginState.LOADING)

            if (::signInDisposable.isInitialized) {
                signInDisposable.dispose()
            }
            signInDisposable = subscribe(repository.signIn(login, password), {
                userInfo = it.userInfo?.convert() ?: UserInfo.empty()
                accessToken = it.accessToken
                state.postValue(LoginState.SUCCESS)
            }, {
                state.postValue(LoginState.ERROR)
            })
        } else {
            state.postValue(LoginState.ERROR)
        }
    }

    fun validateLogin(text: String) = text.isValidLogin()

    fun validatePassword(text: String) = text.isValidPassword()

    fun saveUserData(userInfo: UserInfo, token: String, context: Context) {
        val userStorage = UserStorage(context)
        userStorage.saveUserInfo(userInfo)
        userStorage.saveAccessToken(token)
    }
}