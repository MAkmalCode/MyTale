package com.malbyte.mytale.di

import android.util.Config.DEBUG
import com.malbyte.mytale.data.source.remote.service.APIInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {
    private const val BASE_URL = "https://story-api.dicoding.dev/v1/"

    fun provideApiService(): APIInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIInterface::class.java)
}