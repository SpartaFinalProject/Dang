package com.android.dang.retrofit.kind

import com.google.gson.annotations.SerializedName

data class Body<T>(
    @SerializedName("items")
    val items: Items<T>
)