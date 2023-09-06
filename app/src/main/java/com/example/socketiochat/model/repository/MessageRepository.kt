package com.example.socketiochat.model.repository

import com.example.socketiochat.common.BaseRepository
import com.example.socketiochat.model.Message
import com.ischeck.network.Resource
import okhttp3.ResponseBody

interface MessageRepository: BaseRepository {

    suspend fun postMessage(message: Message): Resource<ResponseBody>

}