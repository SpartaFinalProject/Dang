package com.android.dang.dictionary.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    const val API_AUTHKEY = "live_Io2IL1MzPhQh10xWGoU30VLuDfdxwRx3RVYzcPlyYloIwPKEw0KezxEQt1u4aMxP"
    private const val DOG_BASE_URL = "https://api.thedogapi.com"
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        // 디버그 모드에서는 HTTP 요청 및 응답 내용을 로그에 출력
        //interceptor.level = HttpLoggingInterceptor.Level.BODY

        // OkHttpClient를 생성하고 설정을 추가
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
    //Retrofit 인스턴스를 생성하여 서버와 통신하는 API 요청을 만든다.
    private val dogRetrofit = Retrofit.Builder()//Retrofit을 설정하기 위한 빌더 객체를 생성. 이 객체를 사용하여 API와의 통신을 설정
        .baseUrl(DOG_BASE_URL)// 서버의 기본 URL을 설정
        .addConverterFactory(GsonConverterFactory.create())//JSON데이터를 파싱하기 위한 컨버트 팩토리(Gson)설정
        .client(createOkHttpClient())// OkHttpClient를 설정
        .build()
    // Retrofit을 사용하여 서버와 통신하는 인터페이스를 생성
    val dogNetWork: NetWorkInterface = dogRetrofit.create(NetWorkInterface::class.java)
}