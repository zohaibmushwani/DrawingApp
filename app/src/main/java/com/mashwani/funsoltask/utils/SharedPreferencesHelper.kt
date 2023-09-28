package com.mashwani.funsoltask.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    private const val PREFS_NAME = "drawing_pref"

    const val SELECTED_COLOR = "selectedColor"

    private lateinit var sharedPreferences: SharedPreferences

    // Initialize the SharedPreferences in the Application class or in MainActivity onCreate()
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save a boolean value to SharedPreferences
    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    // Retrieve a boolean value from SharedPreferences, providing a default value if not found
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Save a string value to SharedPreferences
    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    // Retrieve a string value from SharedPreferences, providing a default value if not found
    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Save an integer value to SharedPreferences
    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    // Retrieve an integer value from SharedPreferences, providing a default value if not found
    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    // Save a long value to SharedPreferences
    fun saveLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    // Retrieve a long value from SharedPreferences, providing a default value if not found
    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    // Save a float value to SharedPreferences
    fun saveFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    // Retrieve a float value from SharedPreferences, providing a default value if not found
    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    // Remove a value from SharedPreferences
    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    // Clear all values in SharedPreferences
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
