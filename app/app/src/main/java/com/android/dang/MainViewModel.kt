package com.android.dang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dang.retrofit.abandonedDog.AbandonedShelter

class MainViewModel: ViewModel() {
    val abandonedDogsList: LiveData<List<AbandonedShelter>>
        get() = _abandonedDogsList
    private val _abandonedDogsList =
        MutableLiveData((listOf(AbandonedShelter())))

    fun setAbandonedDogsList(list: List<AbandonedShelter>) {
        _abandonedDogsList.value = list
    }
}