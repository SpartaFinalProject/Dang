package com.android.dang.retrofit.abandonedDog

import com.android.dang.retrofit.kind.Response
import com.android.dangtheland.retrofit.abandonedDog.Item
import com.google.gson.annotations.SerializedName

data class AbandonedDogRes(
    @SerializedName("response")
    val response: Response<AbandonedDog?>
)