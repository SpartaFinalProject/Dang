package com.android.dang.like

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.search.searchItemModel.SearchDogData

class LikeViewModel : ViewModel()  {
    val likeList = MutableLiveData<List<SearchDogData>?>()

    fun addLikeList(list : List<SearchDogData>){
        val currentList = mutableListOf<SearchDogData>()
        currentList.addAll(list)
        Log.d("list5", "${currentList.size}")
        likeList.value = currentList
    }
}