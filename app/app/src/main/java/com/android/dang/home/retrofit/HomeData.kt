package com.android.dang.home.retrofit

data class HomeData(
    val response: Response,

    )
data class Response(
    val header: Header,
    val body: Body,
)

data class Header(
    val reqno: Long,
    val resultcode: Long,
    val resultmsg: String,
)

data class Body(
    val items: Items,
    val numofrows: Long,
    val pageno: Long,
    val totalcount: Long,
)

data class Items(
    val item: List<Item>,
)

data class Item(
    val desertionno: Long,
    val filename: String,
    val happendt: Long,
    val happenPlace: String,
    val kindCd: String,
    val colorCd: String,
    val age: String,
    val weight: String,
    val noticeNo: String,
    val noticesdt: Long,
    val noticeedt: Long,
    val popfile: String,
    val processState: String,
    val sexCd: String,
    val neuterYn: String,
    val specialMark: String,
    val careNm: String,
    val careTel: String,
    val careAddr: String,
    val orgNm: String,
    val chargenm: String,
    val officeTel: String,
)