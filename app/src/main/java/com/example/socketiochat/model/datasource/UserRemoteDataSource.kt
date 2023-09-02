package com.example.socketiochat.model.datasource

import com.example.socketiochat.model.LoginResponse
import com.ischeck.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(
        username: String,
        password: String
    ): LoginResponse = withContext(Dispatchers.IO) {
        apiService.login(username, password)
    }


}