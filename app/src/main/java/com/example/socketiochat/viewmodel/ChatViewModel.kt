package com.example.socketiochat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socketiochat.common.BaseViewModel
import com.example.socketiochat.common.Event
import com.example.socketiochat.model.LoginResponse
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.MessageResponse
import com.example.socketiochat.model.MessageToServer
import com.example.socketiochat.model.repository.MessageRepositoryImpl
import com.ischeck.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val messageRepository: MessageRepositoryImpl): BaseViewModel() {

    private val _messageSendEvent = MutableLiveData<Event<Resource<MessageResponse>>>()
    val messageSendEvent: LiveData<Event<Resource<MessageResponse>>> = _messageSendEvent

    private val _getMessageHistoryEvent = MutableLiveData<Event<Resource<List<Message>>>>()
    val getMessageHistoryEvent: LiveData<Event<Resource<List<Message>>>> = _getMessageHistoryEvent

    fun sendMessage(message: MessageToServer) =
        viewModelScope.launch {
            _messageSendEvent.value = Event(Resource.Loading)
            _messageSendEvent.value = Event(messageRepository.postMessage(message))
        }

    fun getMessageHistory() =
        viewModelScope.launch {
            _getMessageHistoryEvent.value = Event(Resource.Loading)
            _getMessageHistoryEvent.value = Event(messageRepository.getMessageHistory())
        }

}