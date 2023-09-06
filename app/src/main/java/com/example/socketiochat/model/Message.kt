package com.example.socketiochat.model

data class Message (
    var message: String,
    var user: String,
    var time: Long
)