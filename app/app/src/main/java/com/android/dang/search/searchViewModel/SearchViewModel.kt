package com.android.dang.search.searchViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.search.searchItemModel.SearchDogData

class SearchViewModel: ViewModel() {
    val searchesList = MutableLiveData<List<SearchDogData>?>()

    fun searches(list: MutableList<SearchDogData>){
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        searchesList.value = currentList
    }

    fun clearSearches(){
        searchesList.value = null
    }
}