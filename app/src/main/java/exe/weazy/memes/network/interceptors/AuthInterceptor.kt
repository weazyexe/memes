package exe.weazy.memes.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headers = originalRequest.headers().newBuilder()
            .add("Authorization", accessToken)
            .build()

        val newRequest = originalRequest.newBuilder()
            .headers(headers)
            .build()

        return chain.proceed(newRequest)
    }
}