package exe.weazy.memes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import exe.weazy.memes.repository.network.NetworkService
import exe.weazy.memes.repository.network.interceptors.AuthInterceptor
import exe.weazy.memes.storage.UserStorage
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule(private val context: Context) {

    @Provides
    fun provideNetworkService() : NetworkService {
        val storage = UserStorage(context)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(storage))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://demo2407529.mockable.io")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(NetworkService::class.java)
    }
}