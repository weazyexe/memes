package exe.weazy.memes.network

import android.annotation.SuppressLint
import exe.weazy.memes.di.App
import exe.weazy.memes.entity.Credentials
import exe.weazy.memes.entity.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkRepository {

    @Inject
    lateinit var retrofit: Retrofit

    private val service: NetworkService

    init {
        App.getComponent().injectRepository(this)
        service = retrofit.create(NetworkService::class.java)
    }

    @SuppressLint("CheckResult")
    fun signIn(login: String,
               password: String,
               onSuccess: (accessToken: String, userInfo: UserInfo?) -> Unit,
               onError: (t: Throwable) -> Unit) {

        service.signIn(Credentials(login, password))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                onSuccess(it.accessToken, it.userInfo)
            }, {
                onError(it)
            })
    }
}