package com.android.dangtheland.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DangClient {
    val api: Interface
        get() = instanse.create(Interface::class.java)

    private val instanse: Retrofit
        private get(){
            val gson = GsonBuilder().setLenient().create()
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().addNetworkInterceptor(httpInterceptor).build()

            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}