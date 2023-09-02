package com.example.socketiochat.model.repository

import com.example.socketiochat.common.BaseRepository
import com.example.socketiochat.model.LoginResponse
import com.ischeck.network.Resource

interface UsersRepository: BaseRepository {

    suspend fun login(
        username: String,
        password: String
    ): Resource<LoginResponse>

}