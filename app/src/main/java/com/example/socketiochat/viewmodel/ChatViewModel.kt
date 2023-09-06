package com.example.socketiochat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socketiochat.common.BaseViewModel
import com.example.socketiochat.common.Event
import com.example.socketiochat.model.LoginResponse
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.repository.MessageRepositoryImpl
import com.ischeck.network.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val messageRepository: MessageRepositoryImpl): BaseViewModel() {

    private val _messageSendEvent = MutableLiveData<Event<Resource<ResponseBody>>>()
    val messageSendEvent: LiveData<Event<Resource<ResponseBody>>> = _messageSendEvent

    fun sendMessage(message: Message) =
        viewModelScope.launch {
            _messageSendEvent.value = Event(Resource.Loading)
            _messageSendEvent.value = Event(messageRepository.postMessage(message))
        }

}