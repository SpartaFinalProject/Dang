package com.android.dang.home.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INETworkService {
    @GET("abandonmentPublic")
    fun homeDang(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("_type") type: String,
        @Query("upkind") upkind: Int
    ) : Call<HomeData?>
}