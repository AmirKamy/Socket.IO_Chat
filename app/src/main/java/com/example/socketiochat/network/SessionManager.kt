package com.example.socketiochat.network

import android.content.Context
import android.content.SharedPreferences
import com.example.socketiochat.R
import com.example.socketiochat.model.User

class SessionManager (context: Context) {
    private var tokenPrefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private var verifiedPrefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_UID = "user_uid"
        const val USER_USERNAME = "user_username"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = tokenPrefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    fun saveUserProfile(user: User) {
        val editor = verifiedPrefs.edit()
        editor.putString(USER_UID, user.user)
        editor.putString(USER_USERNAME, user.username)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return tokenPrefs.getString(USER_TOKEN, null)
    }
    fun fetchProfile(): User {

        val uid = verifiedPrefs.getString(USER_UID,"")
        val username = verifiedPrefs.getString(USER_USERNAME,"")

        return User(uid!!,username!!)

    }
}