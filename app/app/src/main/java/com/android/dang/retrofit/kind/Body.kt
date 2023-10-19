package com.android.dangtheland.retrofit.kind


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val items: Items
)