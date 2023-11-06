package com.android.dang.retrofit.shelter

import com.google.gson.annotations.SerializedName

data class Shelter(
    @SerializedName("careRegNo")
    val careRegNo: String? = null,

    @SerializedName("careNm")
    val careNm: String? = null
)
