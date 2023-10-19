package com.android.dangtheland.retrofit.abandonedDog


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("age")
    val age: String,
    @SerializedName("careAddr")
    val careAddr: String,
    @SerializedName("careNm")
    val careNm: String,
    @SerializedName("careTel")
    val careTel: String,
    @SerializedName("chargeNm")
    val chargeNm: String,
    @SerializedName("colorCd")
    val colorCd: String,
    @SerializedName("desertionNo")
    val desertionNo: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("happenDt")
    val happenDt: String,
    @SerializedName("happenPlace")
    val happenPlace: String,
    @SerializedName("kindCd")
    val kindCd: String,
    @SerializedName("neuterYn")
    val neuterYn: String,
    @SerializedName("noticeEdt")
    val noticeEdt: String,
    @SerializedName("noticeNo")
    val noticeNo: String,
    @SerializedName("noticeSdt")
    val noticeSdt: String,
    @SerializedName("officetel")
    val officetel: String,
    @SerializedName("orgNm")
    val orgNm: String,
    @SerializedName("popfile")
    val popfile: String,
    @SerializedName("processState")
    val processState: String,
    @SerializedName("sexCd")
    val sexCd: String,
    @SerializedName("specialMark")
    val specialMark: String,
    @SerializedName("weight")
    val weight: String
)