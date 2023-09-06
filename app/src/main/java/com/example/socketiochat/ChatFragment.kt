package com.example.socketiochat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketiochat.adapters.MessageAdapter
import com.example.socketiochat.common.EventObserver
import com.example.socketiochat.databinding.FragmentChatBinding
import com.example.socketiochat.databinding.FragmentMainAuthBinding
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.User
import com.example.socketiochat.network.SessionManager
import com.example.socketiochat.viewmodel.ChatViewModel
import com.ischeck.network.Resource
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import org.json.JSONObject
import java.util.Calendar

class ChatFragment : Fragment() {

    private lateinit var adapter: MessageAdapter
    private lateinit var user: User
    private var _binding: FragmentChatBinding? = null
    private val viewModel: ChatViewModel by viewModels()
    private val binding get() = _binding!!

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MessageAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        setupPusher()

        binding.send.setOnClickListener {
            sendMessage()
        }

        viewModel.messageSendEvent.observe(viewLifecycleOwner, EventObserver{

            resetInput()

            if (it is Resource.Failure) {
                when {
                    it.isNetworkError -> Log.e("error", "network") // connectionErrorAlertDialogMessage()
                    it.errorCode == 500 -> Log.e("error", "500") // error500AlertDialogMessage()
                    it.errorCode == 401 -> Log.e("error", "401") // cantFindAccountDialog()
                    it.errorCode == 422 -> Log.e("error", "422") // cantFindAccountDialog()
                    else -> {
                        // showMessageErrorFromServer(it.errorBody)
                    }
                }
            }

        })

    }

    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster("PUSHER_APP_CLUSTER")

        val pusher = Pusher("PUSHER_APP_KEY", options)
        val channel = pusher.subscribe("chat")

        channel.bind("new_message") { channelName, eventName, data ->
            val jsonObject = JSONObject(data)

            val message = Message(
                jsonObject["user"].toString(),
                jsonObject["message"].toString(),
                jsonObject["time"].toString().toLong()
            )

            requireActivity().runOnUiThread {
                adapter.addMessage(message)
                // scroll the RecyclerView to the last added element
                binding.recyclerView.scrollToPosition(adapter.itemCount - 1);
            }

        }
    }

    private fun resetInput() {
        binding.messageEdittext.text.clear()
    }

    private fun sendMessage() {
        if (binding.messageEdittext.text.isNotEmpty()) {
            val message = Message(
                user.user,
                binding.messageEdittext.text.toString(),
                Calendar.getInstance().timeInMillis
            )
            viewModel.sendMessage(message)
        }else {
            Toast.makeText(requireContext(),"Message should not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}