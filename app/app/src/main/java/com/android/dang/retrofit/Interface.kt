package com.android.dang.retrofit

import com.android.dang.retrofit.abandonedDog.AbandonedDogRes
import com.android.dang.retrofit.kind.Kind
import com.android.dang.retrofit.sido.SidoRes
import com.android.dangtheland.retrofit.abandonedDog.AbandonedDog
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Interface {

    @GET("abandonmentPublic")
    fun abandonedDogSearch(
        @Query("serviceKey") serviceKey: String = Constants.AUTH_HEADER,
        @Query("upkind") upkind: Int = 417000,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int,
        @Query("kind") kind : String
    ): Call<AbandonedDog?>

    @GET("abandonmentPublic")
        fun abandonedDogShelter(
        @Query("serviceKey") serviceKey: String = Constants.AUTH_HEADER,
        @Query("upkind") upkind: Int = 417000,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int,
        @Query("upr_cd") uprCode: String? = null,
        @Query("org_cd") orgCode: String? = null,
        ) : Call<AbandonedDogRes?>

    @GET("kind")
    fun kindSearch(
        @Query("serviceKey") serviceKey: String = Constants.AUTH_HEADER,
        @Query("up_kind_cd") kind: Int = 417000,
        @Query("_type") type: String = "json"
    ): Call<Kind?>

    @GET("sido")
    fun getSidoList(
        @Query("serviceKey") serviceKey: String = Constants.AUTH_HEADER,
        @Query("numOfRows") numOfRows: Int? = null,
        @Query("pageNum") pageNum: Int? = null,
        @Query("_type") type: String = "json"
    ): Call<SidoRes>

    @GET("sigungu")
    fun getSigunguList(
        @Query("serviceKey") serviceKey: String = Constants.AUTH_HEADER,
        @Query("upr_cd") code: String,
        @Query("_type") type: String = "json"
    ): Call<SidoRes>
}