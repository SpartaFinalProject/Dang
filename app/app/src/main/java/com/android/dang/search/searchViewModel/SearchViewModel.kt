package com.android.dang.search.searchViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.search.searchItemModel.SearchDogData

class SearchViewModel : ViewModel() {
    val searchesList = MutableLiveData<List<SearchDogData>?>()
    private var resetList = mutableListOf<SearchDogData>()

    fun searches(list: MutableList<SearchDogData>) {
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        resetList = list
        currentList.addAll(list)
        searchesList.value = currentList
    }

    fun ageFilter(minAge: Int, maxAge: Int) {
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        val ageList = currentList.filter {
            val numberMatch = """\d+""".toRegex().find(it.age)
            val ageInt = numberMatch?.value?.toIntOrNull() ?: 0
            ageInt in minAge..maxAge
        }
        searchesList.value = ageList
    }

    fun genderFilter(gender: String){
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        val genderList = currentList.filter {it.sexCd == gender}
        searchesList.value = genderList
    }

    fun neutrality(gender: String){
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        val genderList = currentList.filter {it.neuterYn == gender}
        searchesList.value = genderList
    }

    fun sizeFilter(minSize: Int, maxSize: Int){
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        val sizeList = currentList.filter {
            val numberMatch = """\d+""".toRegex().find(it.weight)
            val sizeInt = numberMatch?.value?.toIntOrNull() ?: 0
            sizeInt in minSize..maxSize
        }
        searchesList.value = sizeList
    }

    fun resetFilter(){
        searchesList.value = resetList
    }

    fun clearSearches() {
        searchesList.value = null
    }
}