package com.example.socketiochat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socketiochat.network.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {


    private lateinit var sessionManager: SessionManager

    override fun onStart() {
        super.onStart()
        sessionManager = SessionManager(this)

        // login or home
        if (sessionManager.fetchAuthToken() != null)
            startMainActivity()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    private fun startMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}