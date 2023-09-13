package com.example.socketiochat.network

import android.content.Context
import android.content.SharedPreferences
import com.example.socketiochat.R
import com.example.socketiochat.model.User
import com.google.gson.GsonBuilder

class SessionManager (context: Context) {
    private var tokenPrefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private var verifiedPrefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val USER = "user"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = tokenPrefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }
    fun saveUserProfile(user: User) {
        val editor = verifiedPrefs.edit()

        val jsonString = GsonBuilder().create().toJson(user)

        editor.putString(USER, jsonString)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return tokenPrefs.getString(TOKEN, null)
    }
    fun fetchProfile(): User {

        val user = verifiedPrefs.getString(USER,null)

        return GsonBuilder().create().fromJson(user, User::class.java)

    }
}