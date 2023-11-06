package com.android.dang.dictionary.retrofit

import com.android.dang.dictionary.data.BreedsData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("/v1/breeds")
    suspend fun getBreeds(
        @Header("Authorization") token: String?,
        @QueryMap param: HashMap<String, Int>
    ): BreedsData

}
