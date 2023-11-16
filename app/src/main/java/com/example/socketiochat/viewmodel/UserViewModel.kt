package com.example.socketiochat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socketiochat.common.BaseViewModel
import com.example.socketiochat.common.Event
import com.example.socketiochat.model.LoginResponse
import com.example.socketiochat.model.repository.UserRepositoryImpl
import com.ischeck.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepositoryImpl): BaseViewModel() {


    private val _loginEvent = MutableLiveData<Event<Resource<LoginResponse>>>()
    val loginEvent: LiveData<Event<Resource<LoginResponse>>> = _loginEvent


    fun login(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginEvent.value = Event(Resource.Loading)
        _loginEvent.value = Event(userRepository.login(username, password))
    }

}