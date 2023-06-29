package com.ooommm.clontelegramm3.ui.fragments.singlChat

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.AppHolderFactory
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.HolderImageMessage
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.HolderTextMessage
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder.HolderVoiceMessage
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views.MessageView

class SingleChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listMessageCache = mutableListOf<MessageView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent = parent, viewType = viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderImageMessage ->
                holder.drawMessageImage(holder = holder, view = listMessageCache[position])
            is HolderTextMessage ->
                holder.drawMessageText(holder = holder, view = listMessageCache[position])
            is HolderVoiceMessage ->
                holder.drawMessageVoice(holder = holder, view = listMessageCache[position])
            else -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listMessageCache[position].getTypeView()
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




