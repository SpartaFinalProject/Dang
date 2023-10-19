package com.android.dangtheland.retrofit.kind


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("kindCd")
    val kindCd: String,
    @SerializedName("knm")
    val knm: String
)