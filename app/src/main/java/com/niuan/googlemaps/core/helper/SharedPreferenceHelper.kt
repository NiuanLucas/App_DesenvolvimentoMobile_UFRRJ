package com.niuan.googlemaps.core.helper

import android.content.Context
import android.content.SharedPreferences

open class SharedPreferenceHelper(context: Context) {
    private var sharedPreferencesEditor : SharedPreferences.Editor
    private var sharedPreferences : SharedPreferences
    private val TAG_JSON = "json"

    init {
        sharedPreferencesEditor = context.getSharedPreferences("MapsSharedPreferences", Context.MODE_PRIVATE).edit()
        sharedPreferences = context.getSharedPreferences("MapsSharedPreferences", Context.MODE_PRIVATE)
    }

    fun saveJson(json : String){
        sharedPreferencesEditor.putString(TAG_JSON, json)
        sharedPreferencesEditor.apply()
    }

    fun getJson(): String {
        return sharedPreferences.getString(TAG_JSON, "") ?: ""
    }
}