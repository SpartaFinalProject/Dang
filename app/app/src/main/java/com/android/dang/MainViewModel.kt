package com.android.dang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.search.searchItemModel.SearchDogData

class MainViewModel: ViewModel() {
    val abandonedDogsList: LiveData<List<SearchDogData>>
        get() = _abandonedDogsList
    private val _abandonedDogsList =
        MutableLiveData((listOf(SearchDogData())))

    fun setAbandonedDogsList(list: List<SearchDogData>) {
        _abandonedDogsList.value = list
    }

    fun getLikedDog(position: Int): SearchDogData {
        val shelterList = abandonedDogsList.value?.toMutableList() ?: mutableListOf()
        return shelterList[position]
    }
}