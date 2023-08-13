package com.andi.githubuserapplication.di

import com.andi.githubuserapplication.BuildConfig
import com.andi.githubuserapplication.network.ApiService
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

//    @Provides
//    @Singleton
//    fun provideRetrofit():ApiService=
//        Retrofit.Builder()
//            .baseUrl(ApiService.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
}