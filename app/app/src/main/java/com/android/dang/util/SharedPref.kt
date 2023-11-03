package com.android.dang.util

import android.content.Context
import com.android.dang.App
import com.android.dang.home.retrofit.HomeItemModel
import com.google.gson.GsonBuilder

/**
 * Preference Util class
 * 자주 사용하는 타입의 기능을 구현해서 모아둠.
 */
object SharedPref {
    private const val PREF_KEY = "PRETEST_PREF"

    fun setBoolean(key: String?, value: Boolean) {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    fun setInt(key: String?, value: Int) {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).apply()
    }

    fun setLong(key: String?, value: Long) {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putLong(key, value).apply()
    }

    fun setString(key: String?, value: String?) {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String?, defaultVal: Boolean): Boolean {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defaultVal)
    }

    fun getInt(key: String?, defaultVal: Int): Int {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getInt(key, defaultVal)
    }

    fun getLong(key: String?, defaultVal: Long): Long {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getLong(key, defaultVal)
    }

    fun getString(key: String?, defaultVal: String): String {
        val sp = App.instance.applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        return sp.getString(key, defaultVal) ?: ""
    }
}