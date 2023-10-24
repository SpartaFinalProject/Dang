package com.android.dang.retrofit.abandonedDog


import com.android.dangtheland.retrofit.abandonedDog.Response
import com.google.gson.annotations.SerializedName

data class AbandonedDog(
    @SerializedName("response")
    val response: Response
)