package exe.weazy.memes.network

import exe.weazy.memes.entity.Credentials
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {

    @POST
    fun signIn(@Body credentials: Credentials)
}