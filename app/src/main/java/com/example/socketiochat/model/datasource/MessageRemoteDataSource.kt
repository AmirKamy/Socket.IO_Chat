package com.example.socketiochat.model.datasource

import com.example.socketiochat.model.Message
import com.ischeck.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.http.Body
import javax.inject.Inject

class MessageRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun postMessage(body: Message): ResponseBody = withContext(Dispatchers.IO) {
        apiService.postMessage(body)
    }

}