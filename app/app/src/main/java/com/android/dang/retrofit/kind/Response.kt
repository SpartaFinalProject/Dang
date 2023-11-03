package com.android.dang.retrofit.kind


import com.android.dangtheland.retrofit.kind.Header
import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("body")
    val body: Body<T>,
    @SerializedName("header")
    val header: Header
)