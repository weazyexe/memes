package exe.weazy.memes.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.network.NetworkRepository
import exe.weazy.memes.state.LoginState

class LoginViewModel : ViewModel() {

    private val repository = NetworkRepository()

    val state = MutableLiveData<LoginState>(LoginState.Default())

    fun signIn(login: String, password: String) {
        state.postValue(LoginState.Loading())

        repository.signIn(login, password, { token, userInfo ->
            state.postValue(LoginState.Success(token, userInfo))
        }, {
            state.postValue(LoginState.Error())
        })
    }
}