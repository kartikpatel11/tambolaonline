package com.tambolaonline.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder

/**
 * Singleton class for managing preferences for POJO or model class's object.
 **/
object TambolaSharedPreferencesManager {

    //Shared Preference field used to save and retrieve JSON string
    lateinit var preferences: SharedPreferences

    val TAG = "TambolaPreferencesMgr::"

    //Name of Shared Preference file
    private const val PREFERENCES_FILE_NAME = TambolaConstants.TAMBOLA_SHARED_PREFERENCES_FILE

    /**
     * Call this first before retrieving or saving object.
     *
     * @param application Instance of application class
     */
    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        Log.i(TAG,"Storing Json Object in sharedPreference with key=$key & value = $jsonString")

        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     *
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        Log.i(TAG,"Retrieving Json string in sharedPreference with key=$key & value = $value")

        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    /**
     * Used to delete objects from shared preferences
     */
    fun removeAll() {
        preferences.edit().clear().apply()
    }
}