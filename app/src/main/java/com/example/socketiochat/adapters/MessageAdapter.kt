package com.example.socketiochat.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.socketiochat.R
import com.example.socketiochat.model.Message
import com.example.socketiochat.model.User
import com.example.socketiochat.network.SessionManager
import java.text.SimpleDateFormat

class MessageAdapter (val context: Context, val glide: RequestManager) : RecyclerView.Adapter<MessageViewHolder>() {

    private val messages = mutableListOf<Message>()

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.message)
        private var timeText: TextView = view.findViewById(R.id.time)

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = getDateTime(message.created_at)
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.message)
        private var name: TextView = view.findViewById(R.id.name)
        private var timeText: TextView = view.findViewById(R.id.time)
        private var avatar: ImageView = view.findViewById(R.id.avatar)

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = getDateTime(message.created_at)
            glide.load(message.user.avatar).circleCrop().into(avatar)
            name.text = message.user.username
            Log.i("avkir", message.user.avatar)
        }
    }


    fun addHistoryMessage(message: List<Message>){
        message.forEach {
            messages.add(it)
        }
        notifyDataSetChanged()
    }

    fun addNewMessage(newMessage: Message){
        messages.add(0,newMessage)
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

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: String): String {

        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("HH:mm")

        return formatter.format(parser.parse(s))
    }

}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2
