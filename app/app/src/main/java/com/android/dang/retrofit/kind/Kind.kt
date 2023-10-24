package com.android.dang.retrofit.kind


import com.android.dangtheland.retrofit.kind.Response
import com.google.gson.annotations.SerializedName

data class Kind(
    @SerializedName("response")
    val response: Response
)