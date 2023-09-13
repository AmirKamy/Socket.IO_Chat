package com.example.socketiochat.model.repository

import com.example.socketiochat.model.Message
import com.example.socketiochat.model.MessageResponse
import com.example.socketiochat.model.MessageToServer
import com.example.socketiochat.model.datasource.MessageRemoteDataSource
import com.example.socketiochat.model.datasource.UsersRemoteDataSource
import com.ischeck.network.Resource
import okhttp3.ResponseBody
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageRemoteDataSource: MessageRemoteDataSource
): MessageRepository {
    override suspend fun postMessage(message: MessageToServer): Resource<MessageResponse> =  safeApiCall{
        messageRemoteDataSource.postMessage(message)
    }

    override suspend fun getMessageHistory(): Resource<List<Message>> = safeApiCall {
        messageRemoteDataSource.getMessageHistory()
    }


}