package com.android.dangtheland.retrofit.abandonedDog


import com.android.dang.retrofit.abandonedDog.Header
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("body")
    val body: Body,
    @SerializedName("header")
    val header: Header
)