package com.example.socketiochat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.socketiochat.network.SessionManager
import com.ischeck.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun performLogout() = lifecycleScope.launch {

        try{
            apiService.logout()
        }catch (e:Exception){
            Log.e("tag", e.toString())
        }
        sessionManager.saveAuthToken(null)

        val intent = Intent(applicationContext, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}