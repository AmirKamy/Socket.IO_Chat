package com.example.socketiochat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.socketiochat.adapters.MessageAdapter
import com.example.socketiochat.common.EventObserver
import com.example.socketiochat.databinding.FragmentChatBinding
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.MessageToServer
import com.example.socketiochat.model.User
import com.example.socketiochat.network.SessionManager
import com.example.socketiochat.viewmodel.ChatViewModel
import com.google.gson.GsonBuilder
import com.ischeck.network.Resource
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var adapter: MessageAdapter
    private lateinit var user: User
    private var _binding: FragmentChatBinding? = null
    private val viewModel: ChatViewModel by viewModels()
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = SessionManager(requireContext()).fetchProfile()
        viewModel.getMessageHistory()

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL;
        layoutManager.stackFromEnd = true;
        layoutManager.isSmoothScrollbarEnabled = true;
        layoutManager.reverseLayout = true;

        binding.recyclerView.layoutManager = layoutManager

        adapter = MessageAdapter(requireContext(), glide)
        binding.recyclerView.adapter = adapter
        setupPusher()

        binding.send.setOnClickListener {
            sendMessage()
        }

        viewModel.getMessageHistoryEvent.observe(viewLifecycleOwner, EventObserver {

            Log.i("history get", it.toString())

            if (it is Resource.Failure) {
                when {
                    it.isNetworkError -> Log.e(
                        "error",
                        "network"
                    ) // connectionErrorAlertDialogMessage()
                    it.errorCode == 500 -> Log.e("error", "500") // error500AlertDialogMessage()
                    it.errorCode == 401 -> Log.e("error", "401") // cantFindAccountDialog()
                    it.errorCode == 422 -> Log.e("error", "422") // cantFindAccountDialog()
                    else -> {
                        // showMessageErrorFromServer(it.errorBody)
                    }
                }
            }

            if (it is Resource.Success) {
                adapter.addHistoryMessage(it.value)
                binding.recyclerView.scrollToPosition(1)
            }

        })

        viewModel.messageSendEvent.observe(viewLifecycleOwner, EventObserver {

            Log.i("result", it.toString())

            if (it is Resource.Failure) {
                when {
                    it.isNetworkError -> Log.e(
                        "error",
                        "network"
                    ) // connectionErrorAlertDialogMessage()
                    it.errorCode == 500 -> Log.e("error", "500") // error500AlertDialogMessage()
                    it.errorCode == 401 -> Log.e("error", "401") // cantFindAccountDialog()
                    it.errorCode == 422 -> Log.e("error", "422") // cantFindAccountDialog()
                    else -> {
                        // showMessageErrorFromServer(it.errorBody)
                    }
                }
            }

            if (it is Resource.Success) {
                resetInput()
            }

        })

    }

    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster("ap2")

        val pusher = Pusher("a27f49f91acf3e1029af", options)

        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i(
                    "Pusher",
                    "State changed from ${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.i(
                    "Pusher",
                    "There was a problem connecting! code ($code), message ($message), exception($e)"
                )
            }
        }, ConnectionState.ALL)


        val channel = pusher.subscribe("public")

        Log.i("pusher", "pusher")
        channel.bind("chat") { _, _, data ->

            val message = GsonBuilder().create().fromJson(data, PusherMessage::class.java)
            Log.i("msg", data.toString())

            requireActivity().runOnUiThread {

                adapter.addNewMessage(message.message)

                // scroll the RecyclerView to the last added element
                binding.recyclerView.scrollToPosition(1)
            }

        }
    }

    private fun resetInput() {
        binding.messageEdittext.text.clear()
    }

    private fun sendMessage() {
        if (binding.messageEdittext.text.isNotEmpty()) {
            val message = MessageToServer(
                binding.messageEdittext.text.toString(),
            )
            viewModel.sendMessage(message)
        } else {
            Toast.makeText(requireContext(), "Message should not be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

data class PusherMessage(
    val message: Message
)