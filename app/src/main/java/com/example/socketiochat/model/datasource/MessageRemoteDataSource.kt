package com.example.socketiochat.model.datasource

import com.example.socketiochat.model.Message
import com.example.socketiochat.model.MessageResponse
import com.example.socketiochat.model.MessageToServer
import com.ischeck.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.http.Body
import javax.inject.Inject

class MessageRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun postMessage(body: MessageToServer): MessageResponse = withContext(Dispatchers.IO) {
        apiService.postMessage(body)
    }

    suspend fun getMessageHistory(): List<Message> = withContext(Dispatchers.IO) {
        apiService.getMessageHistory()
    }

}