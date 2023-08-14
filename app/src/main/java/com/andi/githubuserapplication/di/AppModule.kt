package com.andi.githubuserapplication.di

import android.app.Application
import android.content.Context
import com.andi.githubuserapplication.BuildConfig
import com.andi.githubuserapplication.data.local.room.UserDao
import com.andi.githubuserapplication.data.local.room.UserDatabase
import com.andi.githubuserapplication.network.ApiService
import com.andi.githubuserapplication.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(authInterceptor: Interceptor): ApiService =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", BuildConfig.KEY)
                .build()
            chain.proceed(requestHeaders)
        }
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun getDb(context:Context):UserDatabase{
        return UserDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun getDao(userDatabase: UserDatabase):UserDao{
        return userDatabase.userDao()
    }
    @Provides
    @Singleton
    fun getAppExecutor(): AppExecutors {
        return AppExecutors()
    }

}