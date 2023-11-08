package com.android.dang.retrofit.abandonedDog


import com.google.gson.annotations.SerializedName

data class Header(
    @SerializedName("reqNo")
    val reqNo: Int,
    @SerializedName("resultCode")
    val resultCode: String,
    @SerializedName("resultMsg")
    val resultMsg: String,
    @SerializedName("errorMsg")
    val errorMsg: String
)