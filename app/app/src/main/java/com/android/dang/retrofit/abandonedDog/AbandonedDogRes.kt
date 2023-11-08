package com.android.dang.retrofit.abandonedDog

import com.android.dang.retrofit.kind.Response
import com.android.dang.search.searchItemModel.SearchDogData
import com.google.gson.annotations.SerializedName

data class AbandonedDogRes(
    @SerializedName("response")
    val response: Response<SearchDogData?>
)