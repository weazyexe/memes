package exe.weazy.memes.network

import exe.weazy.memes.di.App
import exe.weazy.memes.network.requests.LoginPasswordRequest
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkRepository {

    @Inject
    lateinit var service: NetworkService

    init {
        App.getComponent().injectRepository(this)
    }

    fun signIn(login: String, password: String) = service.signIn(LoginPasswordRequest(login, password))

    fun fetchMemes() = service.fetchMemes()
}