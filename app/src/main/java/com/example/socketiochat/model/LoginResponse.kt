package com.example.socketiochat.model

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val uid: String,
    val username: String
)
