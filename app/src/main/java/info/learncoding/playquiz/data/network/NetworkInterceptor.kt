package info.learncoding.playquiz.data.network

import android.content.Context
import info.learncoding.playquiz.utils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (context.isNetworkAvailable()) {
            chain.proceed(chain.request())
        } else {
            throw IOException("No Internet Connection")
        }
    }
}