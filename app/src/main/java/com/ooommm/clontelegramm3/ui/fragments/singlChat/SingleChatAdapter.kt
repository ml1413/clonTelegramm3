package com.ooommm.clontelegramm3.ui.fragments.singlChat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.AppHolderFactory
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.HolderImageMessage
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.HolderTextMessage
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.asTime
import com.ooommm.clontelegramm3.utilits.downloadAndSetImage

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listMessageCache = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent = parent, viewType = viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderImageMessage -> drawMessageImage(holder = holder, position = position)
            is HolderTextMessage -> drawMessageText(holder = holder, position = position)
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listMessageCache[position].getTypeView()
    }

    private fun drawMessageImage(holder: HolderImageMessage, position: Int) {

        if (listMessageCache.get(position).from == CURRENT_UID) {
            // отключаем блок приходящеко изображения и включаем блок исходяшего изображения
            holder.blockUserImage.isVisible = true
            holder.blockReceivedImage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserImage.downloadAndSetImage(listMessageCache.get(position).fileUrl)
            holder.chatUserImageTime.text =
                listMessageCache.get(position).timeStamp.asTime()
        } else {
            //отображаем приходящее сообщение
            holder.blockUserImage.isVisible = false
            holder.blockReceivedImage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedImage.downloadAndSetImage(listMessageCache.get(position).fileUrl)
            holder.chatReceivedImageTime.text =
                listMessageCache.get(position).timeStamp.asTime()
        }
    }

    private fun drawMessageText(holder: HolderTextMessage, position: Int) {

        if (listMessageCache.get(position).from == CURRENT_UID) {
            //отобразить исходящее  и скрыть входящее
            holder.blockUserMessage.isVisible = true
            holder.blocReceivedMessage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserMessage.text = listMessageCache.get(position).text
            holder.chatUserMessageTime.text =
                listMessageCache.get(position).timeStamp.asTime()
        } else {
            //отобразить входящее и скрыть исходящее
            holder.blockUserMessage.isVisible = false
            holder.blocReceivedMessage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedMessage.text = listMessageCache.get(position).text
            holder.chatReceivedMessageTime.text =
                listMessageCache.get(position).timeStamp.asTime()

        }
    }

    override fun getItemCount() = listMessageCache.size

    fun addItemToBottom(item: MessageView, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            notifyItemInserted(listMessageCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: MessageView, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            listMessageCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }

}




