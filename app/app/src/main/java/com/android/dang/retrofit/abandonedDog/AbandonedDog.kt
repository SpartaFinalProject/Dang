package com.android.dangtheland.retrofit.abandonedDog


import com.google.firebase.firestore.GeoPoint
import com.android.dangtheland.retrofit.abandonedDog.Response
import com.google.gson.annotations.SerializedName
data class AbandonedDog(
    @SerializedName("response")
    val response: Response
)