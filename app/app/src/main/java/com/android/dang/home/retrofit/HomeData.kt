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
    val happenplace: String,
    val kindcd: String,
    val colorcd: String,
    val age: String,
    val weight: String,
    val noticeno: String,
    val noticesdt: Long,
    val noticeedt: Long,
    val popfile: String,
    val processstate: String,
    val sexcd: String,
    val neuteryn: String,
    val specialmark: String,
    val carenm: String,
    val caretel: String,
    val careaddr: String,
    val orgnm: String,
    val chargenm: String,
    val officetel: String,
)