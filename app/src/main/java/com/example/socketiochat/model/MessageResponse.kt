package com.example.socketiochat.model

data class MessageResponse(
    val created_at: String,
    val id: String,
    val message: String,
    val user_id: String
)