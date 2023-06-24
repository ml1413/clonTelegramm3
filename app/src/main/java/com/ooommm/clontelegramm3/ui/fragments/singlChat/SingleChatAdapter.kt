package com.ooommm.clontelegramm3.ui.fragments.singlChat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.CURRENT_UID
import com.ooommm.clontelegramm3.utilits.DiffUtilCallBack
import com.ooommm.clontelegramm3.utilits.assTime

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var listMessageCache = emptyList<CommonModel>()
    private lateinit var diffResult: DiffUtil.DiffResult

    /////////////////////////////////////////////////////////////////////////////////////////////
    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blockUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_user_message)
        val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
        val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_time)

        val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_receiver_message)
        val chatReceivedMessage: TextView = view.findViewById(R.id.chat_receiver_message)
        val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_receiver_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mesage_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (listMessageCache.get(position).from == CURRENT_UID) with(holder) {
            blockUserMessage.isVisible = true
            blocReceivedMessage.isVisible = false
            chatUserMessage.text = listMessageCache.get(position).text
            chatUserMessageTime.text =
                listMessageCache.get(position).timeStamp.toString().assTime()
        } else with(holder) {
            blockUserMessage.isVisible = false
            blocReceivedMessage.isVisible = true
            chatReceivedMessage.text = listMessageCache.get(position).text
            chatReceivedMessageTime.text =
                listMessageCache.get(position).timeStamp.toString().assTime()

        }
    }

    override fun getItemCount() = listMessageCache.size


    fun addItem(item: CommonModel) {
        val newList = mutableListOf<CommonModel>()
        newList.addAll(listMessageCache)
        if (! newList.contains(item)) newList.add(item)
        newList.sortBy { it.timeStamp.toString() }
        diffResult = DiffUtil.calculateDiff(DiffUtilCallBack(listMessageCache, newList))
        diffResult.dispatchUpdatesTo(this)
        listMessageCache = newList
    }
}




