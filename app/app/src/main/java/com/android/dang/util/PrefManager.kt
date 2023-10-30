package com.android.dang.util

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.android.dang.home.retrofit.HomeItemModel
import com.google.gson.GsonBuilder

object PrefManager {

    fun addItem(context: Context, item: HomeItemModel) {
        val pref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val gson = GsonBuilder().create()
        editor.putString(item.popfile, gson.toJson(item))
        editor.apply()
    }

    fun deleteItem(context: Context, popfile: String) {
        val pref = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.remove(popfile)
        editor.apply()
    }

    fun getLikeItem(context: Context): ArrayList<HomeItemModel> {
        val pref = context.getSharedPreferences("PREF_NAME", Activity.MODE_PRIVATE)
        val allEntries: Map<String, *> = pref.all
        val like = ArrayList<HomeItemModel>()
        val gson = GsonBuilder().create()
        for ((key, value) in allEntries) {
            val item = gson.fromJson(value as String, HomeItemModel::class.java)
            like.add(item)
            Log.d("SharedPreferences", "Key: $key, Value: $value")

        }
        return like
    }
}