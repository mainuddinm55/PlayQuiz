package info.learncoding.playquiz.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.learncoding.playquiz.BuildConfig
import info.learncoding.playquiz.data.network.ApiClient
import info.learncoding.playquiz.data.network.NetworkInterceptor
import info.learncoding.playquiz.data.repository.QuizRepository
import info.learncoding.playquiz.data.repository.QuizRepositoryImp
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().setPrettyPrinting().create()
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 5L * 1024L * 1024L // 5 MB
        return Cache(File(context.cacheDir, "${context.packageName}.cache"), cacheSize)
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(@ApplicationContext context: Context): NetworkInterceptor {
        return NetworkInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(networkInterceptor: NetworkInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
            callTimeout(1, TimeUnit.MINUTES)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            addInterceptor(networkInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiClient(gson: Gson, okHttpClient: OkHttpClient): ApiClient {
        return Retrofit.Builder()
            .baseUrl("https://herosapp.nyc3.digitaloceanspaces.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(apiClient: ApiClient): QuizRepository {
        return QuizRepositoryImp(apiClient)
    }
}















