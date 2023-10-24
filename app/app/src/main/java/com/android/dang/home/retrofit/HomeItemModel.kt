package com.android.dang.home.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeItemModel(
    val popfile: String?,
    val kindCd: String?,
    val age: String?,
    val specialMark: String?,
    val happenPlace: String?,
    val orgNm: String?,
    val processState: String?,
    var isLiked: Boolean = false
): Parcelable{

}