package com.example.socketiochat.model

data class Message (
    var id: String,
    var message: String,
    var user: User,
    var created_at: String,
)
