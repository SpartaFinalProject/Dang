package com.android.dang.retrofit.sido

import com.android.dang.retrofit.kind.Response
import com.google.gson.annotations.SerializedName

data class SidoRes(
    @SerializedName("response")
    val response: Response<Sido>
)
