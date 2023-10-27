package com.android.dang.search.searchViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RecentViewModel: ViewModel() {
    val recentList = MutableLiveData<List<String>?>()


    fun saveRecent(list : List<String>){
        val currentList = recentList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        recentList.value = currentList
    }
    fun recentAdd(text: String){
        val currentList = recentList.value?.toMutableList() ?: mutableListOf()
        currentList.add(text)
        currentList.reverse()
        recentList.value = currentList
    }

    fun recentRemove(position: Int){
        val currentList = recentList.value?.toMutableList() ?: mutableListOf()
        currentList.removeAt(position)
        recentList.value = currentList
    }

    fun editText(position: Int): String {
        val currentList = recentList.value?.toMutableList() ?: mutableListOf()
        return currentList[position]
    }

    fun recentReset(){
        recentList.value = null

    }

    fun saveListToPreferences(context: Context){
        val list = recentList.value ?: mutableListOf()
        val sharedPreferences = context.getSharedPreferences("recentWord", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("recentWord", json)
        editor.apply()
    }

    fun getListFromPreferences(context: Context): List<String>{
        val sharedPreferences = context.getSharedPreferences("recentWord", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("recentWord", null)
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type) ?: listOf()
    }
}