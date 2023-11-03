package com.android.dang.retrofit.kind


import com.android.dangtheland.retrofit.kind.Item
import com.google.gson.annotations.SerializedName

data class Items<T>(
    @SerializedName("item")
    val item: List<T>
)