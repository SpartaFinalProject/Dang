//package com.android.dang.search
//
//import android.content.Context
//import com.android.dang.search.searchItemModel.SearchDogData
//import com.android.dang.util.PrefManager
//
//object LikeList {
//    var likeList = mutableListOf<SearchDogData>()
//
//    fun addLikeList(data: SearchDogData){
//        likeList.add(data)
//    }
//
//    fun deleteLikeList(data: SearchDogData){
//        for ((index, list) in likeList.withIndex()){
//            if (list.popfile == data.popfile){
//                likeList.removeAt(index)
//                break
//            }
//        }
//    }
//
//    fun retrieveLikeList(): List<SearchDogData>{
//        return likeList
//    }
//
//    fun saveList(context: Context){
//        likeList.addAll(PrefManager.getLikeItem(context))
//    }
//}