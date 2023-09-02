package com.example.socketiochat.model.repository

import com.example.socketiochat.model.LoginResponse
import com.example.socketiochat.model.datasource.UsersRemoteDataSource
import com.ischeck.network.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource
): UsersRepository {
    override suspend fun login(username: String, password: String): Resource<LoginResponse> =
        safeApiCall { usersRemoteDataSource.login(username, password) }

}