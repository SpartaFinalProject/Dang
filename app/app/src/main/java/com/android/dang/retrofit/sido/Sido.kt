package com.android.dang.retrofit.sido

import com.google.gson.annotations.SerializedName

data class Sido(
    @SerializedName("orgCd")
    val orgCd: String = "",

    @SerializedName("orgdownNm")
    val orgdownNm: String = "",

    @SerializedName("uprCd")
    val uprCd: String? = null
)
