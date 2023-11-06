package com.android.dang.retrofit.shelter

import com.android.dang.retrofit.kind.Response
import com.google.gson.annotations.SerializedName

data class ShelterRes (
    @SerializedName("respnose")
    val response: Response<Shelter>
)