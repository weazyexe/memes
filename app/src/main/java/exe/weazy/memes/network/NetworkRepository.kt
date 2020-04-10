package exe.weazy.memes.network

import exe.weazy.memes.di.App
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkRepository {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        App.getComponent().injectRepository(this)
    }

    fun signIn(login: String, password: String) {

    }
}