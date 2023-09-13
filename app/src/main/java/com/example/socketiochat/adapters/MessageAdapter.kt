package com.example.socketiochat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socketiochat.R
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.User
import com.example.socketiochat.network.SessionManager
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter (val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {

    private val messages: ArrayList<Message> = ArrayList()

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.message)
        private var timeText: TextView = view.findViewById(R.id.time)

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = message.created_at
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.message)
        private var timeText: TextView = view.findViewById(R.id.time)

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = message.created_at
        }
    }


    fun addMessage(message: List<Message>){
        message.forEach {
            messages.add(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        val user: User = SessionManager(context).fetchProfile()

        return if(user.id == message.user.id) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_right, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_left, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.bind(message)
    }
}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}

object DateUtils {
    fun fromMillisToTimeString(millis: Long): String {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(millis)
    }
}

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2
