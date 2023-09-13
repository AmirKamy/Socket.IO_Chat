package com.example.socketiochat.model.repository

import com.example.socketiochat.common.BaseRepository
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.MessageResponse
import com.example.socketiochat.model.MessageToServer
import com.ischeck.network.Resource
import okhttp3.ResponseBody

interface MessageRepository: BaseRepository {

    suspend fun postMessage(message: MessageToServer): Resource<MessageResponse>

    suspend fun getMessageHistory(): Resource<List<Message>>

}