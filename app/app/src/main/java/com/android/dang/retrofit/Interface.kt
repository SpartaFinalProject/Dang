package com.android.dang.retrofit

import com.android.dang.retrofit.abandonedDog.AbandonedDog
import com.android.dang.retrofit.kind.Kind
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Interface {

    @GET("abandonmentPublic")
    fun abandonedDogSearch(
        @Query("serviceKey") serviceKey : String = Constants.AUTH_HEADER,
        @Query("upkind") upkind : Int = 417000,
        @Query("_type") type : String = "json",
        @Query("numOfRows") numOfRows : Int,
        @Query("kind") kind : String
    ): Call<AbandonedDog?>

    @GET("kind")
    fun kindSearch(
        @Query("serviceKey") serviceKey : String = Constants.AUTH_HEADER,
        @Query("up_kind_cd") kind : Int = 417000,
        @Query("_type") type : String = "json"
    ): Call<Kind?>
}