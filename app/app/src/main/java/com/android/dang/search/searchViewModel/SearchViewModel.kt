package com.android.dang.search.searchViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.search.searchItemModel.SearchDogData

class SearchViewModel : ViewModel() {
    val searchesList = MutableLiveData<List<SearchDogData>?>()

    var likeList = MutableLiveData<List<SearchDogData>?>()
    private var resetList = mutableListOf<SearchDogData>()

    data class FilterCriteria(
        var minAge: Int? = null,
        var maxAge: Int? = null,
        var gender: String? = null,
        var neuterYn: String? = null,
        var minSize: Int? = null,
        var maxSize: Int? = null
    )

    private var currentFilter = FilterCriteria()

    fun searches(list: MutableList<SearchDogData>) {
        resetList.clear()
        resetList.addAll(list)
        applyFilters(currentFilter)
    }

    fun applyFilters(criteria: FilterCriteria) {
        currentFilter = criteria

        val filteredList = resetList.filter { item ->
            val ageInt = item.age.toIntOrNull() ?: 0
            val sizeInt = item.weight.toIntOrNull() ?: 0

            (criteria.minAge == null || ageInt >= criteria.minAge!!) &&
                    (criteria.maxAge == null || ageInt <= criteria.maxAge!!) &&
                    (criteria.gender == null || item.sexCd == criteria.gender) &&
                    (criteria.neuterYn == null || item.neuterYn == criteria.neuterYn) &&
                    (criteria.minSize == null || sizeInt >= criteria.minSize!!) &&
                    (criteria.maxSize == null || sizeInt <= criteria.maxSize!!)
        }

        searchesList.value = filteredList
    }

    fun ageFilter(minAge: Int, maxAge: Int) {
        currentFilter.minAge = minAge
        currentFilter.maxAge = maxAge
        applyFilters(currentFilter)
    }

    fun genderFilter(gender: String) {
        currentFilter.gender = gender
        applyFilters(currentFilter)
    }

    fun neutrality(neuterStatus: String) {
        currentFilter.neuterYn = neuterStatus
        applyFilters(currentFilter)
    }

    fun sizeFilter(minSize: Int, maxSize: Int) {
        currentFilter.minSize = minSize
        currentFilter.maxSize = maxSize
        applyFilters(currentFilter)
    }

    fun resetAgeFilter() {
        currentFilter.minAge = null
        currentFilter.maxAge = null
        applyFilters(currentFilter)
    }

    fun resetGenderFilter() {
        currentFilter.gender = null
        applyFilters(currentFilter)
    }

    fun resetNeuterFilter() {
        currentFilter.neuterYn = null
        applyFilters(currentFilter)
    }

    fun resetSizeFilter() {
        currentFilter.minSize = null
        currentFilter.maxSize = null
        applyFilters(currentFilter)
    }

    fun resetAllFilters() {
        currentFilter = FilterCriteria()
        applyFilters(currentFilter)
    }

    fun clearSearches() {
        searchesList.value = null
    }

    fun likeList(position: Int){
        val currentList = searchesList.value?.toMutableList() ?: mutableListOf()
        val like = likeList.value?.toMutableList() ?: mutableListOf()

        if (!currentList[position].isLiked){
            like.add(currentList[position])
        } else {
            like.removeIf { it.popfile == currentList[position].popfile }
        }
        likeList.value = like
    }
}
