package exe.weazy.memes.data

import exe.weazy.memes.data.network.NetworkService
import exe.weazy.memes.data.network.requests.LoginPasswordRequest
import exe.weazy.memes.di.App
import javax.inject.Inject

class AuthRepository {

    @Inject
    lateinit var service: NetworkService

    init {
        App.getComponent().injectAuthRepository(this)
    }

    fun signIn(login: String, password: String) = service.signIn(LoginPasswordRequest(login, password))
}