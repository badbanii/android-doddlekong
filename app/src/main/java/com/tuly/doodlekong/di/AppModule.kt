package com.tuly.doodlekong.di

import android.content.Context
import com.google.gson.Gson
import com.tuly.doodlekong.data.remote.api.SetupApi
import com.tuly.doodlekong.util.Constants.HTTP_BASE_URL
import com.tuly.doodlekong.util.Constants.HTTP_BASE_URL_LOCALHOST
import com.tuly.doodlekong.util.Constants.USE_LOCALHOST
import com.tuly.doodlekong.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGsonInstance(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context:Context)=context
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Singleton
    @Provides
    fun provideSetupApi(okHttpClient: OkHttpClient): SetupApi
    {
        return Retrofit.Builder()
            .baseUrl(if(USE_LOCALHOST) HTTP_BASE_URL_LOCALHOST else HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(SetupApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider():DispatcherProvider{
        return object:DispatcherProvider{
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val main: CoroutineDispatcher
                get()= Dispatchers.Main

        }
    }
}